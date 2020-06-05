package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.teacher.entity.EduSubject;
import com.guli.teacher.entity.vo.SubjectVo;
import com.guli.teacher.entity.vo.SubjectVo2;
import com.guli.teacher.mapper.EduSubjectMapper;
import com.guli.teacher.service.EduSubjectService;
import com.guli.util.PoiUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author hxq
 * @since 2020-03-20
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 导入课程
     * @param file
     * @return
     */
    @Override
    @Transactional
    public List<String> importSubject(MultipartFile file)  {
        List<String> msg=new ArrayList<>();
        String filename=file.getOriginalFilename();
        //文件后缀名
        boolean checkExtensions = PoiUtils.checkExtensions(filename);
        //文件内容
        boolean execlFile = false;
        try {
            execlFile = PoiUtils.isExeclFile(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //判断是否为excel文件
        if(!checkExtensions||!execlFile){
            msg.add("请上传excel文件");
            return  msg;
        }
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建工作簿
        try (Workbook workbook=new XSSFWorkbook(inputStream);){
            //获取sheet
            Sheet sheet = workbook.getSheetAt(0);
            //判断有sheet
            if(sheet==null)
            {
                msg.add("当前Excel无内容");
                return  msg;
            }
            //获取sheet行数
            int lastRowNum = sheet.getLastRowNum();
            if(lastRowNum<=1)
            {
                msg.add("当前Excel没有需要导入的内容");
                return  msg;
            }
            //用来判断验证是否通过
            boolean flag=true;
            for (int i = 1; i <=lastRowNum ; i++){
                //获取每行的第一列
                Row checkRow = sheet.getRow(i);
                Cell checkfirstCell =checkRow.getCell(0);
                //检查每行的第一列是否有值
                if(checkfirstCell==null|| StringUtils.isEmpty(checkfirstCell.getStringCellValue()))
                {
                    flag=false;
                    msg.add("当前Excel第"+(i+1)+"行,第1列没有值，请重新填写后上传");
                    continue;
                }
            }
            if(flag){
                //父ID
                String pid="";
                for (int i = 1; i <=lastRowNum ; i++) {
                    //获取每行的第一列
                    Row row = sheet.getRow(i);
                    Cell firstCell =row.getCell(0);
                    //一级分类
                    String firstTitle = firstCell.getStringCellValue();
                    EduSubject subByTitle = this.getSubByTitle(firstTitle);
                    //判断是否已经存在
                    if(subByTitle==null)
                    {
                        //不存在,添加
                        EduSubject eduSubject=new EduSubject();
                        eduSubject.setParentId("0");
                        eduSubject.setSort(1);
                        eduSubject.setTitle(firstTitle);
                        baseMapper.insert(eduSubject);
                        pid=eduSubject.getId();
                    }else {
                        //存在
                        pid=subByTitle.getId();
                    }
                    //获取每行的第二列

                    Cell secondCell =row.getCell(1);
                    //检查每行的第2列是否有值,有值继续进行判断是否添加，没值进行下一轮的循环
                    if(secondCell!=null&& !StringUtils.isEmpty(secondCell.getStringCellValue()))
                    {
                        String secondCellValue = secondCell.getStringCellValue();
                        EduSubject subByTitleAndPid = this.getSubByTitleAndPid(secondCellValue,pid);
                        if(subByTitleAndPid==null)
                        {
                            //不存在,添加
                            EduSubject eduSubject2=new EduSubject();
                            eduSubject2.setParentId("0");
                            eduSubject2.setSort(1);
                            eduSubject2.setParentId(pid);
                            eduSubject2.setTitle(secondCellValue);
                            baseMapper.insert(eduSubject2);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 根据title获取课程
     * @param title
     * @return
     */
    @Override
    public EduSubject getSubByTitle(String title) {
        QueryWrapper wrapper=new QueryWrapper<EduSubject>();
        wrapper.eq("title",title);
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return  eduSubject;
    }

    /**
     * 根据title和父级Id获取课程
     * @param title
     * @return
     */
    @Override
    public EduSubject getSubByTitleAndPid(String title, String pid) {
        QueryWrapper wrapper=new QueryWrapper<EduSubject>();
        wrapper.eq("title",title);
        wrapper.eq("parent_id",pid);
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return  eduSubject;
    }

    /**
     * 获取课程分类
     * @return
     */
    @Override
    public List<SubjectVo> getSubjects() {
        List<SubjectVo> subjectVoList=new ArrayList<>();
        QueryWrapper<EduSubject> parentSubjectWrapper = new QueryWrapper<>();
        parentSubjectWrapper.eq("parent_id","0");
        List<EduSubject> eduSubjects = baseMapper.selectList(parentSubjectWrapper);
        eduSubjects.forEach(p->{
            SubjectVo subjectVo1=new SubjectVo();
            BeanUtils.copyProperties(p,subjectVo1);
            QueryWrapper<EduSubject> childrenWrapper=new QueryWrapper<EduSubject>();
            childrenWrapper.eq("parent_id",p.getId());
            List<EduSubject> eduSubjects2= baseMapper.selectList(childrenWrapper);
            for (EduSubject eduSubject: eduSubjects2) {
                SubjectVo2 subjectVo2=new SubjectVo2();
                BeanUtils.copyProperties(eduSubject,subjectVo2);
                subjectVo1.getChildren().add(subjectVo2);
            }

            subjectVoList.add(subjectVo1);
        });
        return subjectVoList;
    }

    /**
     * 根据Id查询子节点
     * @param id
     * @return
     */
    @Override
    public List<String> getChildrenSubject(String id) {
        List<String> chidrenSubjectTitle= new ArrayList<>();
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        List<EduSubject> eduSubjects = baseMapper.selectList(wrapper);
        eduSubjects.forEach(p->{
            chidrenSubjectTitle.add(p.getTitle());
        });
        return  chidrenSubjectTitle;
    }

    /**
     * 删除课程分类
     * @param id
     * @return
     */
    @Override
    @Transactional
    public boolean deleteSubject(String id) {
        try {
            List<String> chidrenSubjectTitle= new ArrayList<>();
            QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
            wrapper.eq("parent_id",id);
            List<EduSubject> eduSubjects = baseMapper.selectList(wrapper);
            eduSubjects.forEach(p->{
                 baseMapper.deleteById(p.getId());
            });
            baseMapper.deleteById(id);
            return  true;
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }
    }

    /**
     * 添加subject
     * @param subject
     * @return
     */
    public boolean addSubject(EduSubject subject)
    {
        int insert = baseMapper.insert(subject);
        return  insert==1;
    }
}
