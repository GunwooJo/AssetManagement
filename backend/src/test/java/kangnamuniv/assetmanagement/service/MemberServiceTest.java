package kangnamuniv.assetmanagement.service;

import org.junit.jupiter.api.Test;

class MemberServiceTest {

    MemberService memberService = new MemberService();

    @Test
    void generateConnectedId() {
        String result = memberService.addAccount("BK", "1", "0081", "kanggi1997", "temp", "971101");

        System.out.println("result = " + result);
    }
}
