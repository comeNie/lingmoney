package com.mrbt.lingmoney.testng;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mrbt.lingmoney.mapper.HxRedPacketMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.model.HxRedPacket;
import com.mrbt.lingmoney.model.HxRedPacketExample;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.UsersRedPacketExample;
import com.mrbt.lingmoney.utils.DateUtils;

/**
 * 
 * @author Administrator
 *
 */
public class GiftCardTest extends SpringTestNG {
	
	@Autowired
	private UsersRedPacketMapper usersRedPacketMapper;
	
	@Autowired
	private HxRedPacketMapper hxRedPacketMapper;
	
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;

	private static final int ACQUIS_MODE_1 = 1;
	private static final int ACQUIS_MODE_2 = 2;
	private static final int ACQUIS_MODE_3 = 3;
	private static final int ACQUIS_MODE_4 = 4;
	private static final int ACQUIS_MODE_5 = 5;
	/**
	 * 初始化
	 */
	@BeforeClass
	public void setUp() {
		LOGGER.info("赠送加息券测试类");
	}

	/**
	 * 
	 */
	@Test
	public void test() {
		//登录(注册)赠送
//		giftCardToUser("0FB0CBBA4BBBD06CB5D84F8E3683D586", ACQUIS_MODE_1);
		//开通E账户
//		giftCardToUser("0FB0CBBA4BBBD06CB5D84F8E3683D586", ACQUIS_MODE_2);
		//激活E账户
//		giftCardToUser("0FB0CBBA4BBBD06CB5D84F8E3683D586", ACQUIS_MODE_3);
		//首次理财(产品成立后)
		giftCardToUser("0FB0CBBA4BBBD06CB5D84F8E3683D586", ACQUIS_MODE_4);
		//理财(产品成立后)
//		giftCardToUser("0FB0CBBA4BBBD06CB5D84F8E3683D586", ACQUIS_MODE_5);
	}
	
	/**
	 * 赠送卡券
	 * 登录赠送，要求用户的注册日期必须在卡券的赠送开始时间和结束时间之间
	 * 
	 * @param uId	uId
	 * @param acquisMode	获取方式
	 */
	public void giftCardToUser(String uId, int acquisMode) {
		Date date = new Date();
		//如果用户要领取的是注册的卡券，要求用户的注册时间在卡券的开始领取时间和结束领取时间之间
		if (acquisMode == 1) {
			UsersProperties up = usersPropertiesMapper.selectByuId(uId);
			date = up.getRegDate();
		}
		//查询卡券表中是否有能够赠送的卡券数据
		HxRedPacketExample hrpe = new HxRedPacketExample();
		hrpe.createCriteria().andAcquisModeEqualTo(acquisMode).andAStartTimeLessThanOrEqualTo(date).andAEndTimeGreaterThanOrEqualTo(date);
		
		List<HxRedPacket> listHrp = hxRedPacketMapper.selectByExample(hrpe);
		
		//如果没有直接跳过，如果有进行验证用户是否有领取过，如果领取过，跳过，如果没有进行领取
		if (listHrp != null && listHrp.size() > 0) {
			for (HxRedPacket hxRedPacket : listHrp) {
				insertPacketToUser(uId, hxRedPacket);
			}
		}
	}
	
	/**
	 * 
	 * @param uId	uId
	 * @param hxRedPacket	封装红包
	 */
	public void insertPacketToUser(String uId, HxRedPacket hxRedPacket) {
		
		UsersRedPacketExample urpe = new UsersRedPacketExample();
		urpe.createCriteria().andUIdEqualTo(uId).andRpIdEqualTo(hxRedPacket.getId());
		
		List<UsersRedPacket> listUrp = usersRedPacketMapper.selectByExample(urpe);
		if (listUrp == null || listUrp.size() == 0) {
			UsersRedPacket insertUrp = new UsersRedPacket();
			insertUrp.setuId(uId);
			insertUrp.setRpId(hxRedPacket.getId());
			insertUrp.setStatus(0);
			insertUrp.setCreateTime(new Date());
			
			//如果延后天数不为空，卡券的有效期是当前日期+延后天数，否则为卡券的有效期
			if (hxRedPacket.getDelayed() != null && hxRedPacket.getDelayed() > 0) {
				insertUrp.setValidityTime(DateUtils.addDay2(new Date(), hxRedPacket.getDelayed() + 1));
			} else {
				insertUrp.setValidityTime(hxRedPacket.getValidityTime());
			}
			usersRedPacketMapper.insert(insertUrp);
		}
	}
}
