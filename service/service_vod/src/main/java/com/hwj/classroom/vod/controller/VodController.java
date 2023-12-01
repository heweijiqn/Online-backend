/**
 * @author 何伟健
 * @version 1.0
 * @date 2023/11/3 20:42
 */


package com.hwj.classroom.vod.controller;

import com.hwj.classroom.vod.service.VodService;
import com.hwj.exception.HwjException;
import com.hwj.result.Result;
import com.hwj.classroom.until.ConstantPropertiesUtil;
import com.hwj.classroom.until.Signature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Api(tags = "腾讯云点播")
@RestController
@RequestMapping("/admin/vod")
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频接口
    @PostMapping("upload")
    public Result upload() {
        String fileId = vodService.updateVideo();
        return Result.ok(fileId);
    }


    @DeleteMapping("remove/{fileId}")
    public Result remove(@PathVariable String fileId) {
        vodService.removeVideo(fileId);
        return Result.ok(null);
    }

    @GetMapping("sign")
    @ApiOperation(value = "获取签名")
    public Result sign() {
        Signature sign = new Signature();
        // 设置 App 的云 API 密钥
        sign.setSecretId(ConstantPropertiesUtil.ACCESS_KEY_ID);
        sign.setSecretKey(ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        sign.setCurrentTime(System.currentTimeMillis() / 1000);
        sign.setRandom(new Random().nextInt(java.lang.Integer.MAX_VALUE));
        sign.setSignValidDuration(3600 * 24 * 2); // 签名有效期：2天
        try {
            String signature = sign.getUploadSignature();
            System.out.println("signature : " + signature);
            return Result.ok(signature);
        } catch (Exception e) {
            System.out.print("获取签名失败");
            e.printStackTrace();
            throw new HwjException(20001,"获取签名失败");
        }
    }

}
