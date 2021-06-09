package com.fishpound.accountservice.controller;

import com.fishpound.accountservice.entity.Notice;
import com.fishpound.accountservice.result.JsonResult;
import com.fishpound.accountservice.result.ResultTool;
import com.fishpound.accountservice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    /**
     * 获取用户未读消息条数
     * @param uid
     * @return
     */
    @GetMapping("/count")
    public JsonResult countUnreadNotice(@RequestParam(value = "uid") String uid){
        return ResultTool.success(noticeService.findNoticeUnread(uid));
    }

    /**
     * 获取消息详细内容
     * @param id
     * @return
     */
    @GetMapping()
    public JsonResult getNotice(@RequestParam(value = "id") Integer id){
        return ResultTool.success(noticeService.getOne(id));
    }

    /**
     * 获取消息列表（分页）
     * @param uid
     * @param page
     * @return
     */
    @GetMapping("/notices")
    public JsonResult getAllByUser(@RequestParam(value = "uid") String uid,
                                   @RequestParam(value = "page", defaultValue = "1") Integer page)
    {
        return ResultTool.success(noticeService.findAllByUser(uid, page));
    }

    /**
     * 将所有未读消息(stats=true)设置为已读(stats=false)
     * @param uid 用户id
     * @return
     */
    @GetMapping("/notices/read")
    public JsonResult setNoticeRead(@RequestParam(value = "uid") String uid){
        List<Notice> all = noticeService.findAll(uid);
        for(Notice item : all){
            if(item.getState()){
                item.setState(false);
                noticeService.updateNotice(item);
            }
        }
        return ResultTool.success();
    }

    /**
     * 设定一条消息为已读
     * @param nid
     * @return
     */
    @GetMapping("/read")
    public JsonResult setNoticeRead(@RequestParam(value = "notice_id") Integer nid){
        Notice notice = noticeService.getOne(nid);
        notice.setState(false);
        noticeService.updateNotice(notice);
        return ResultTool.success();
    }
}
