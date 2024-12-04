package pintoss.giftmall.domains.payment.controller;

import com.galaxia.api.merchant.Message;
import com.galaxia.api.util.ChecksumUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pintoss.giftmall.domains.TestPaymentService;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Log4j2
@Controller
@RequestMapping("/sample")
@RequiredArgsConstructor
public class RenewPaymentController {

    private final TestPaymentService testPaymentService;

    @GetMapping(value = "/test-input")
    public ModelAndView payInputPage() {
        ModelAndView mv = new ModelAndView();

        // 옵션 리스트 생성 및 모델에 추가 (900:신용카드/1100:핸드폰)
        List<String> paymentOptions = Arrays.asList( "0900", "1100");

        mv.addObject("paymentOptions", paymentOptions);
        mv.addObject("serviceId", "M2103135");
        mv.addObject("amount", "1004");
        mv.addObject("itemName", "테스트상품_123");
        mv.addObject("userId", "user_id");
        mv.addObject("userName", "홍길동");
        mv.addObject("orderId", "test_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        mv.addObject("orderDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        mv.addObject("returnUrl", "http://localhost:88/sample/payreturn");//실용서버로 변경시 	pin-toss.com로 변경하기.
        mv.addObject("reserved1", "예비변수1");
        mv.addObject("reserved2", "예비변수2");
        mv.addObject("reserved3", "예비변수3");
        mv.addObject("cancelFlag", "N");

        mv.setViewName("payinput");

        return mv;
    }

    //결제입력창의 체크썸 생성
    @ResponseBody
    @PostMapping("/check-sum")
    public String generateCheckSum(@RequestParam(value = "CheckSum", required = false) String reqCheckSum, Model model) throws Exception {
        log.info("request::"+reqCheckSum);
        String respCheckSum = "";
        if (reqCheckSum != null) {
            respCheckSum  = ChecksumUtil.genCheckSum(reqCheckSum);
            log.info("response::"+respCheckSum);
            model.addAttribute("respCheckSum",respCheckSum);
        } else {
            return "Error: Checksum generation failed.";
        }
        return respCheckSum;
    }

    //결제 결과
    @PostMapping("/payreturn")
    public String handleReturn(@RequestParam Map<String, String> params, Model model) {
        try {
            // Populate model with request parameters
            String serviceId = params.get("SERVICE_ID");
            String serviceCode = params.get("SERVICE_CODE");
            String message = params.get("MESSAGE");
            String responseCode = params.get("RESPONSE_CODE");

            model.addAttribute("serviceId", serviceId);
            model.addAttribute("serviceCode", serviceCode);
            model.addAttribute("responseCode", responseCode);
            model.addAttribute("responseMessage", params.get("RESPONSE_MESSAGE"));
            model.addAttribute("transactionId", params.get("TRANSACTION_ID"));
            model.addAttribute("detailResponseCode", params.get("DETAIL_RESPONSE_CODE"));
            model.addAttribute("detailResponseMessage", params.get("DETAIL_RESPONSE_MESSAGE"));

            // responseCode가 성공 코드인 경우 응답 처리
            if ("0000".equals(responseCode)) {
                Map<String, String> authInfo = new HashMap<>();
                authInfo.put("serviceId", serviceId);
                authInfo.put("serviceCode", serviceCode);
                authInfo.put("message", message);

                Message respMsg = testPaymentService.MessageAuthProcess(authInfo);
                //RenewPayment payment = testPaymentService.saveRenewPayment();
                log.info("responseMsg::::"+respMsg);
                // 승인 응답 처리
                model.addAttribute("authDate", respMsg.get("1005"));
                model.addAttribute("authAmount", respMsg.get("1007"));
                model.addAttribute("outResponseCode", respMsg.get("1002"));
                model.addAttribute("outResponseMessage", respMsg.get("1003"));
            }
        } catch (Exception e){
            e.getMessage();
        }
        return "payreturn";
    }

    //환불 페이지
    @GetMapping("/refund")
    public ModelAndView refundPage() {
        ModelAndView model = new ModelAndView();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String currentTime = now.format(formatter);

        model.addObject("serviceId", "M2103135");
        model.addObject("orderId", "cancel_" + currentTime);
        model.addObject("orderDate", currentTime);
        model.addObject("cancelUrl", "http://localhost:88/sample/refund-process");
        model.setViewName("cancelInput");
        return model;
    }

//    @PostMapping("/refund-process")
//    public ModelAndView processRefund(@RequestBody RefundRequestDTO dto) {
//        ModelAndView mav = new ModelAndView("cancelResult"); // 결과 페이지 뷰 이름 설정
//
//        try {
//            // 요청 처리 로직
//            Map<String, Object> response = new HashMap<>();
//            response.put("refundDate", LocalDateTime.now());
//            response.put("transactionId", dto.getTransactionId());
//            response.put("responseCode", "0000");
//            response.put("responseMessage", "환불이 성공적으로 처리되었습니다.");
//            response.put("detailResponseCode", "1000");
//            response.put("detailResponseMessage", "정상 처리");
//            response.put("cancelAmount", dto.getCancelAmount() != null ? dto.getCancelAmount() : "전액취소");
//
//            // 결과 데이터를 모델에 추가
//            mav.addObject("response", response);
//        } catch (Exception e) {
//            // 에러 메시지를 모델에 추가
//            mav.addObject("error", "취소 요청 중 에러가 발생했습니다. 관리자에게 문의하세요.");
//            log.error("환불 처리 중 예외 발생", e);
//        }
//
//        return mav; // 결과 페이지로 이동
//    }

    @PostMapping("/refund-process")
    public ModelAndView processRefund(@RequestParam Map<String, String> params) {
        ModelAndView mav = new ModelAndView("cancelResult"); // 결과 페이지 뷰 이름

        try {
            Map<String, Object> response = new HashMap<>();
            response.put("refundDate", LocalDateTime.now());
            response.put("transactionId", params.get("transactionId"));
            response.put("responseCode", "0000");
            response.put("responseMessage", "환불이 성공적으로 처리되었습니다.");
            response.put("detailResponseCode", "1000");
            response.put("detailResponseMessage", "정상 처리");
            response.put("cancelAmount", params.get("cancelAmount") != null ? params.get("cancelAmount") : "전액취소");

            mav.addObject("response", response); // 결과 데이터 추가
        } catch (Exception e) {
            mav.addObject("error", "취소 요청 중 에러가 발생했습니다. 관리자에게 문의하세요.");
        }

        return mav; // 결과 페이지로 이동
    }
}
