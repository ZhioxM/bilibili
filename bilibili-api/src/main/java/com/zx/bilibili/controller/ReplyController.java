package com.zx.bilibili.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.zx.bilibili.bo.VideoRootCommentBO;
import com.zx.bilibili.common.api.CommonResult;
import com.zx.bilibili.domain.VideoComment;
import com.zx.bilibili.service.ReplyService;
import com.zx.bilibili.vo.VideoCommentVo;
import com.zx.bilibili.vo.VideoRootCommentVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论相关
 *
 * @Author: Mzx
 * @Date: 2023/1/16 14:47
 */
@RestController
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    /**
     * 添加视频评论
     */
    @SaCheckLogin
    @PostMapping("/video/comment")
    public CommonResult addVideoComment(@RequestBody VideoComment videoComment) {
        Long userId = StpUtil.getLoginIdAsLong();
        videoComment.setUserId(userId);
        replyService.addVideoComment(videoComment);
        return CommonResult.success(null);
    }

    @ApiOperation("分页查询一级评论")
    @GetMapping("/video/comment/{videoId}")
    public CommonResult<List<VideoRootCommentVo>> listVideoRootComment(@PathVariable Long videoId,
                                                                       @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                                       @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                                       @RequestParam(required = false, defaultValue = "0") Integer orderBy) {
        List<VideoRootCommentBO> bos = replyService.listVideoRootComment(videoId, pageNum, pageSize, orderBy);
        List<VideoRootCommentVo> vos = new ArrayList<>(bos.size());
        for (VideoRootCommentBO bo : bos) {
            VideoRootCommentVo vo = new VideoRootCommentVo(bo.getRootComment().getId(),
                                                           bo.getRootComment().getVideoId(),
                                                           bo.getRootComment().getUserId(),
                                                           bo.getRootComment().getReplyUserId(),
                                                           bo.getRootComment().getCreateTime(),
                                                           bo.getRootComment().getContext(),
                                                           bo.getTotalReply());
            List<VideoCommentVo> reply = bo.getTopKSubComment().stream().map(
                    o -> new VideoCommentVo(o.getId(), o.getVideoId(), o.getUserId(), o.getReplyUserId(), o.getRootId(), o.getParentId(), o.getCreateTime(), o.getContext())).collect(Collectors.toList());
            vo.setReply(reply);
            vos.add(vo);
        }
        return CommonResult.success(vos);
    }

    @ApiOperation("分页查询子评论")
    @GetMapping("/video/comment/{videoId}/{commentId}")
    public CommonResult<List<VideoCommentVo>> listVideoSubComment(@PathVariable Long videoId,
                                                                  @PathVariable Long commentId,
                                                                  @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                                  @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        // 二级评论也可以做分页
        List<VideoComment> subComments = replyService.listVideoSubComment(videoId, commentId, pageNum, pageSize);
        List<VideoCommentVo> vos = subComments.stream().map(
                o -> new VideoCommentVo(o.getId(), o.getVideoId(), o.getUserId(), o.getReplyUserId(), o.getRootId(), o.getParentId(), o.getCreateTime(), o.getContext())).collect(Collectors.toList());
        return CommonResult.success(vos);
    }
}
