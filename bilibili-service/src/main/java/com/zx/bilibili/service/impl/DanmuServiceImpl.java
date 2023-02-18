package com.zx.bilibili.service.impl;

import com.zx.bilibili.config.RedisKey;
import com.zx.bilibili.domain.Danmu;
import com.zx.bilibili.domain.DanmuExample;
import com.zx.bilibili.mapper.DanmuMapper;
import com.zx.bilibili.service.DanmuService;
import com.zx.bilibili.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @Author: Mzx
 * @Date: 2023/2/18 14:47
 */
@Service
public class DanmuServiceImpl implements DanmuService {
    @Autowired
    private RedisService redisService;

    @Autowired
    private DanmuMapper danmuMapper;

    @Override
    public List<Danmu> listDanmu(Long videoID, String startTime, String endTime) throws ParseException {
        String danmuKey = RedisKey.DAN_MU + RedisKey.DOT + videoID;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = sdf.parse(startTime);
        Date endDate = sdf.parse(endTime);
        if (redisService.hasKey(danmuKey)) {
            // 从redis中查询
            List<Danmu> danmuList = redisService.lRange(danmuKey, 0, redisService.lSize(danmuKey) - 1).stream().map(o -> (Danmu) o).filter(new Predicate<Danmu>() {
                @Override
                public boolean test(Danmu danmu) {
                    return danmu.getCreateTime().after(startDate) && danmu.getCreateTime().before(endDate);
                }
            }).collect(Collectors.toList());
            return danmuList;
        } else {
            // 查询数据库
            DanmuExample danmuExample = new DanmuExample();
            danmuExample.createCriteria().andVideoIdEqualTo(videoID).andCreateTimeBetween(startDate, endDate);
            List<Danmu> danmuList = danmuMapper.selectByExample(danmuExample);
            // 保存到redis中
            redisService.lPushAll(danmuKey, danmuList.toArray());
        }
        return null;
    }
}
