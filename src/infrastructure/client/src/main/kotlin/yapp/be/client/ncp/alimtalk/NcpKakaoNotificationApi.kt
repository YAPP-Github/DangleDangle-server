package yapp.be.client.ncp.alimtalk

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import yapp.be.client.ncp.alimtalk.model.NcpKakaoNotificationModel

@FeignClient(value = "ncpKakaoNotificationFeignClient", url = "https://sens.apigw.ntruss.com/alimtalk/v2")
interface NcpKakaoNotificationApi {
    @PostMapping(value = ["/services/{serviceId}/messages"])
    fun request(
        @RequestHeader("x-ncp-apigw-timestamp") timeStamp: String,
        @RequestHeader("x-ncp-iam-access-key") subAccountAccessKey: String,
        @RequestHeader("x-ncp-apigw-signature-v2") apiGatewaySignature: String,
        @PathVariable serviceId: String,
        @RequestBody request: NcpKakaoNotificationModel.Request
    ): NcpKakaoNotificationModel.Response
}
