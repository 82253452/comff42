package com.f4w.controller;


import com.f4w.annotation.CurrentUser;
import com.f4w.annotation.NotTokenIntecerpt;
import com.f4w.entity.BusiQuestion;
import com.f4w.entity.SysUser;
import com.f4w.mapper.BusiQuestionMapper;
import com.f4w.utils.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/busiQuestion")
public class BusiQuestionController {
    @Resource
    public BusiQuestionMapper busiQuestionMapper;

    @PostMapping("/importQue")
    @Transactional
    public R importQue(@CurrentUser SysUser user, String appId, @RequestParam("file") MultipartFile file) {
        String[] fileTypes = file.getOriginalFilename().split("\\.");
        String type = fileTypes[fileTypes.length - 1];
        try {
            if (StringUtils.contains(type, "xls")) {
                dealXls(user.getId(), appId, file);
            }
            if (StringUtils.contains(type, "doc")) {
                dealDoc(user.getId(), appId, file);
            }
            return R.ok("导入完成");
        } catch (Exception e) {
            log.error("导入异常");
            log.error(e.getMessage());
        }
        return R.error("导入异常");
    }

    private void dealDoc(Integer uid, String appId, MultipartFile file) {
        String text = "";
        try {
            BufferedInputStream is = new BufferedInputStream(file.getInputStream());
            if (FileMagic.valueOf(is) == FileMagic.OLE2) {
                WordExtractor ex = new WordExtractor(is);
                text = ex.getText();
                ex.close();
            } else if (FileMagic.valueOf(is) == FileMagic.OOXML) {
                XWPFDocument doc = new XWPFDocument(is);
                XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
                text = extractor.getText();
                extractor.close();
            }
            saveContentNew(uid, appId, text);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private void dealXls(Integer uid, String appId, MultipartFile file) {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(xssfWorkbook.getFirstVisibleTab());
            XSSFRow row;
            int i = 0;
            String t = "";
            StringBuffer data = new StringBuffer();
            DataFormatter formatter = new DataFormatter();
            while (true) {
                row = xssfSheet.getRow(i);
                t = formatter.formatCellValue(row.getCell(0));
                if ("end".equals(t)) {
                    break;
                }
                i++;
                data.append(t);
                data.append("\n");
                if (i > 10000) {
                    break;
                }
            }
            String d = data.toString();
            saveContentNew(uid, appId, d);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @GetMapping("/selectByPage")
    public R selectByPage(@CurrentUser SysUser user, @RequestParam Map map) {
        Example example = new Example(BusiQuestion.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uid", user.getId());
        if (StringUtils.isNotBlank(MapUtils.getString(map, "title"))) {
            criteria.andLike("title", "%" + MapUtils.getString(map, "title") + "%");
        }
        example.orderBy("ctime").desc();
        PageHelper.startPage(MapUtils.getIntValue(map, "page", 1), MapUtils.getIntValue(map, "rows", 10));
        List<BusiQuestion> list = busiQuestionMapper.selectByExample(example);
        PageInfo<BusiQuestion> page = new PageInfo<>(list);
        return R.ok().put("data", page);
    }

    @PostMapping("/insert")
    public R insert(@CurrentUser SysUser user, @RequestBody BusiQuestion BusiQuestion) {
        BusiQuestion.setUid(user.getId());
        int r = busiQuestionMapper.insert(BusiQuestion);
        return R.ok().put("data", r);
    }

    @GetMapping("/selectById")
    public R selectById(String id) {
        BusiQuestion r = busiQuestionMapper.selectByPrimaryKey(id);
        return R.ok().put("data", r);
    }

    @PostMapping("/updateById")
    public R updateById(@RequestBody BusiQuestion BusiQuestion) {
        int r = busiQuestionMapper.updateByPrimaryKeySelective(BusiQuestion);
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteByIds")
    public R deleteByIds(@RequestBody String ids) {
        int r = busiQuestionMapper.deleteByIds(ids);
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteById")
    public R deleteById(@RequestBody Map param) {
        int r = busiQuestionMapper.deleteByPrimaryKey(MapUtils.getInteger(param, "id"));
        return R.ok().put("data", r);
    }

    @PostMapping("/deleteAll")
    public R deleteAll(@CurrentUser SysUser sysUser) {
        Example example = new Example(BusiQuestion.class);
        example.createCriteria()
                .andEqualTo("uid", sysUser.getId());
        int r = busiQuestionMapper.deleteByExample(example);
        return R.ok().put("data", r);
    }

    private boolean saveContentNew(Integer uid, String appId, String s) {
        String pattern = "\\d+\\.*．*\\s*【(.*)】\\s*(.*(?:(?:\\(|（)\\s*)([A-Fa-f√×YXyx对错是否]{0,4})(?:\\s*(?:）|\\))).*)" +
                "(?:\\s*[A-Fa-f](.*)\\s*)?(?:\\s*[A-Fa-f](.*)\\s*)?(?:\\s*[A-Fa-f](.*)\\s*)?(?:\\s*[A-Fa-f](.*)\\s*)?" +
                "(?:\\s*.*[答案：](.*)\\s*)?";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);
        while (m.find()) {
            //获取题目类型
            String type = m.group(1);
            //第3 8位答案
            String answer8 = getAnswer(m, 3, 8);
            if (StringUtils.isBlank(answer8)) {
                continue;
            }
            if (StringUtils.isNotBlank(type)) {
                if ("判断题".equals(type)) {
                    dealPD(uid, appId, answer8, m);
                } else {
                    dealXZ(uid, appId, answer8, m);
                }
            } else {
                //判断题
                if (StringUtils.containsAny(answer8, "对", "y", "正", "v", "√", "错", "是", "否", "×", "x")) {
                    dealPD(uid, appId, answer8, m);
                }
                //选择题
                else if (StringUtils.containsAny(answer8, "a", "b", "c", "d")) {
                    dealXZ(uid, appId, answer8, m);
                }
            }
        }
        return true;
    }

    private String getAnswer(Matcher m, int... i) {
        for (int i1 : i) {
            String s = StringUtils.deleteWhitespace(m.group(i1));
            if (StringUtils.isNotBlank(s)) {
                return s.toLowerCase();
            }
        }
        return "";
    }

    private boolean dealXZ(Integer uid, String appId, String answer8, Matcher m) {
        String title = m.group(2);
        if (StringUtils.isBlank(title)) {
            return false;
        }
        String options = getOption(m, 4, 5, 6, 7);
        if (StringUtils.isBlank(options)) {
            return false;
        }
        return saveEntity(uid, appId, title, answer8.length() > 1 ? 2 : 1, options.substring(0, options.length() - 1), answer8);
    }

    private String getOption(Matcher m, int... i) {
        String o = "";
        for (int i1 : i) {
            String s = m.group(i1);
            if (StringUtils.isNotBlank(s)) {
                o += s.replaceAll("、", "") + "&";
            }
        }
        return o;
    }

    private boolean dealPD(Integer uid, String appId, String answer8, Matcher m) {
        String title = m.group(2);
        //title replace
        if (StringUtils.isBlank(title)) {
            return false;
        }
        //title getAnswer
        return saveEntity(uid, appId, title, 3, null, answer8);
    }

    private boolean saveEntity(Integer uid, String appId, String title, Integer typeP, String options, String answer) {
        BusiQuestion busiQuestion = new BusiQuestion();
        busiQuestion.setUid(uid);
        busiQuestion.setAnswer(getAnswer(answer));
        busiQuestion.setAppId(appId);
        busiQuestion.setTitle(title.replaceAll("[A-Da-d]", ""));
        busiQuestion.setType(typeP);
        if (typeP != 3) {
            busiQuestion.setQuestions(options);
        }
        busiQuestionMapper.insert(busiQuestion);
        return true;
    }

    private String getAnswer(String answer) {
        if (StringUtils.containsAny(answer, "对", "y", "正", "v", "√")) {
            return "1";
        }
        if (StringUtils.containsAny(answer, "错", "x", "×")) {
            return "0";
        }
        //选择题
        String r = "";
        if (StringUtils.containsAny(answer, "a", "b", "c", "d")) {
            for (int i1 = 0; i1 < answer.length(); i1++) {
                r += String.valueOf("abcd".indexOf(answer.charAt(i1)));
            }
        }
        return r;
    }
}





