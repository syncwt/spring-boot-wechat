package com.senthink.www;

import com.senthink.www.util.SyncWechatToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class ColumbiaApplicationTests {

    @Autowired
    private SyncWechatToken syncWechatToken;

//    @Test
//    public void contextLoads() {
//    }
//
//
//    @Autowired
//    private IUserMapper userMapper;
//
//    @Autowired
//    private BumpProtocolFrameworkService frameworkService;
//
//    @Autowired
//    private BumpProtocolAnalyzeService protocolAnalyzeService;
//
//    @Autowired
//    private BumpDvcBaseService dvcBaseService;
//
//    @Test
//    @Rollback
//    public void findByName() throws Exception {
//        userMapper.insert("AAA", "aaaaa");
//        User u = userMapper.findByName("AAA");
//        Assert.assertEquals("aaaaaaa", u.getPassword());
//    }
//
//    @Test
//    @Rollback
//    public void coventData2Object() throws BusinessException {
//        byte[] bytes = {(byte)0xEC, 0x02,0x11, (byte)0x07, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, (byte)0x0A,(byte)0x0A, 0x0B, (byte)0x0C,(byte)0x0C, 0x0D,0x0D,0x00,0x78,0x00,0x78, (byte)0x88, 0x68};
//        byte[] bytes1 = {(byte) 0xEC, 0x02, 0x02, 0x68};
//        dvcBaseService.receiveMsg("LSD20914819D2E8",bytes);
//
//    }
//
//    @Test
//    public void getRealTime() throws BusinessException {
//        String history = protocolAnalyzeService.getHistory();
//        System.out.println();
//
//    }

    @Test
    public void testSyncTask() {
        System.out.println(syncWechatToken.syncToken());

    }

//    @Test
//    @Rollback
//    public void findByName() throws Exception {
//        userMapper.insert("AAA", "aaaaa");
//        User u = userMapper.findByName("AAA");
//        Assert.assertEquals("aaaaaaa", u.getPassword());
//    }
//
//    @Test
//    @Rollback
//    public void coventData2Object() throws BusinessException {
//        byte[] bytes = {(byte)0xEC, 0x02,0x10, (byte)0x07, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, (byte)0x0A,(byte)0x0A, 0x0B, (byte)0x0C,(byte)0x0C,0x0D,0x00,0x78,0x00,0x78, (byte)0x7A, 0x68};
//        byte[] bytes1 = {(byte) 0xEC, 0x01, 0x01,0x01,0x03,0x68};
//        String s = EncodeUtils.encodeBase64(bytes1);
//        dvcBaseService.receiveMsg("LSD20914819D2E8",bytes);
//
//    }
//
//    @Test
//    public void getRealTime() throws BusinessException {
//        String history = protocolAnalyzeService.getHistory();
//        System.out.println();
//
//    }
}
