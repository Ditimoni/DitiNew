package utils;

public class APIResources {

	static String loyalty_env;
	static String version;
	static String atc_vendor_cd;
	static String atc_channel_type_cd;
	static String atc_website_id;
	static String customerId;
	static String bearerToken_code;
	static String promo_env;
	static String program_version;
	static String dvp_env;
	static String dvp_version;
	static String CCN;
	String resourceUrl = null;
	static String brandVal;
	static String clientCode;
	static String brandname;
	static String ccnLookup_env;

	/**
	 * Author: A Tejaswini
	 * Date: 31/3/2022(Modified)
	 * Description:
	 */
	public static void getResourceProperty() throws Exception {
		ccnLookup_env = JsonReader.getResourceValue("ccnLookup_env");
		loyalty_env = JsonReader.getResourceValue("loyalty_env");
		System.out.println("loyalty env is" + loyalty_env);
		version = JsonReader.getCommonResourceValue("version");

		atc_vendor_cd = JsonReader.getResourceValue("atc", "vendor", "cd");
		atc_channel_type_cd = JsonReader.getResourceValue("atc", "channel", "type", "cd");
		//atc_website_id = JsonReader.getResourceValue("atc", "website", "id");
		customerId = JsonReader.getResourceValue(brandname, "customerId");
		promo_env = JsonReader.getResourceValue("promo_env");
		dvp_env = JsonReader.getResourceValue("dvp_env");
		program_version = JsonReader.getResourceValue("program_version");
		dvp_version = JsonReader.getResourceValue("dvp_version");
		CCN = JsonReader.getResourceValue(brandname, "CCN");
	}

	/**
	 * Author: A Tejaswini
	 * Date: 31/3/2022(Modified)
	 * Description:
	 */
	public static void getTokenResourceProperty() throws Exception {
		bearerToken_code = JsonReader.getCommonResourceValue("bearerToken_code");
	}

	/**
	 * Author: A Tejaswini
	 * Date: 31/3/2022(Modified)
	 * Description:
	 */
	public static void getbrandVal(String brand) {
		if (brand.equalsIgnoreCase("blackandmild")) {
			brandVal = "1000";
			clientCode = "BlackandMild";
		} else if (brand.equalsIgnoreCase("chesterfield")) {
			brandVal = "60";
			clientCode = "Chesterfield";
		} else if (brand.equalsIgnoreCase("copenhagen")) {
			brandVal = "2400";
			clientCode = "Copenhagen";
		} else if (brand.equalsIgnoreCase("iqos")) {
			brandVal = "400";
			clientCode = "IQOS";
		} else if (brand.equalsIgnoreCase("lm")) {
			brandVal = "13";
			clientCode = "LM";
		} else if (brand.equalsIgnoreCase("marlboro")) {
			brandVal = "27";
			clientCode = "MARLBORO";
		} else if (brand.equalsIgnoreCase("myparliament")) {
			brandVal = "5";
			clientCode = "MyParliament";
		} else if (brand.equalsIgnoreCase("redseal")) {
			brandVal = "2700";
			clientCode = "redseal";
		} else if (brand.equalsIgnoreCase("skoal")) {
			brandVal = "2000";
			clientCode = "Skoal";
		} else if (brand.equalsIgnoreCase("virginiaslims")) {
			brandVal = "7";
			clientCode = "VirginiaSlims";
		} else if (brand.equalsIgnoreCase("h20")) {
			brandVal = "6000";
			clientCode = "h20";
		}
	}

	/**
	 * Author: A Tejaswini
	 * Date: 31/3/2022(Modified)
	 * Description:
	 */
	public String getResource(String resource) {
		try {
			getTokenResourceProperty();
			switch (resource) {
				case "PostTokenApi":
					resourceUrl = "/" + bearerToken_code + "/" + "oauth2" + "/" + "token";
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resourceUrl;
	}

	/**
	 * Author: A Tejaswini
	 * Date: 31/3/2022(Modified)
	 * Description:
	 */
	public String getResource(String resource, String brand) {
		try {
			brandname = brand;
			getResourceProperty();
			APIResources.getbrandVal(brand);

			switch (resource) {
				case "MultiCCNLookup_Api_FiltersNotMatch":
				case "MultiCCNLookup_Api_NoFilter":
				case "MultiCCNLookup_Api_NoCCN":
				case "MultiCCN_InvalidCCN_specific":
				case "MultiCCNLookup_Api_specific":
				case "MultiCCNLookup_Api":
					resourceUrl = "/" + ccnLookup_env + "/" + version + "/" + "multipleccnlookup" + "/" + atc_vendor_cd + "/" + atc_channel_type_cd + "/"
							+ brandVal;
					break;
				case "GetOrdersApi":
					resourceUrl = "/" + dvp_env + "/" + dvp_version + "/" + "order" + "/" + CCN + "/" + clientCode;
					break;
				case "CCNLookup_Api_NoInputFlag":
				case "CCNLookup_Api_InvalidCCN":
				case "CCNLookup_Api_InvalidAddress":
				case "CCNLookup_Api_specificFlags":
				case "CCNLookup_Api":
					resourceUrl = "/" + ccnLookup_env + "/" + version + "/" + "ccnlookup" + "/" + atc_vendor_cd + "/" + atc_channel_type_cd + "/"
							+ brandVal;
					break;
				case "GET_reward_api_NegRedeem":
				case "GET_reward_api_InvalidToken":
				case "GET_reward_api_Wishlist":
				case "GetRewardApi":

				case "GET_reward_api_detailsEnrolled":
					resourceUrl = "/" + loyalty_env + "/" + version + "/" + atc_vendor_cd + "/" + atc_channel_type_cd + "/"
							+ brandVal + "/" + "consumer" + "/" + customerId + "/" + "rewards";
					break;

				case "GET_reward_api_detailsNotEnrolled":

				case "GET_AllRewards_api":
					resourceUrl = "/" + loyalty_env + "/" + version + "/" + atc_vendor_cd + "/" + atc_channel_type_cd + "/"
							+ brandVal + "/" + "rewards";
					break;

				case "GET_reward_Category_api":
					resourceUrl = "/" + loyalty_env + "/" + version + "/" + atc_vendor_cd + "/" + atc_channel_type_cd + "/"
							+ brandVal + "/" + "reward" + "/" + "category";
					break;

				case "webActivities_GetAPI":
					resourceUrl = "/" + loyalty_env + "/" + version + "/" + atc_vendor_cd + "/" + atc_channel_type_cd + "/"
							+ brandVal + "/" + "consumer" + "/" + customerId + "/" + "webActivities";
					break;

				case "PostTokenApi":
					resourceUrl = "/" + bearerToken_code + "/" + "oauth2" + "/" + "token";
					break;

				case "transactionHistory_GetAPI":
					resourceUrl = "/" + loyalty_env + "/" + version + "/" + atc_vendor_cd + "/" + atc_channel_type_cd + "/"
							+ brandVal + "/" + "consumer" + "/" + customerId + "/" + "lastTransactions";
					break;

				case "Add_reward_to_wishlist":
					resourceUrl = "/" + loyalty_env + "/" + version + "/" + atc_vendor_cd + "/" + atc_channel_type_cd + "/"
							+ brandVal + "/" + "consumer" + "/" + customerId + "/" + "wishlist";
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resourceUrl;
	}

	/**
	 * Author: A Tejaswini
	 * Date: 31/3/2022(Modified)
	 * Description:
	 */
	public String getResource(String resource, String code, String brand) {
		try {
			brandname = brand;
			getResourceProperty();
			APIResources.getbrandVal(brand);
			switch (resource) {
				case "GetRewardApi":
					resourceUrl = "/" + loyalty_env + "/" + version + "/" + atc_vendor_cd + "/" + atc_channel_type_cd + "/"
							+ brandVal + "/" + "consumer" + "/" + customerId + "/" + "rewards";
					break;

				case "webactivity_api":
					resourceUrl = "/" + loyalty_env + "/" + version + "/" + atc_vendor_cd + "/" + atc_channel_type_cd + "/"
							+ brandVal + "/" + "consumer" + "/" + customerId + "/" + "earn";
					break;
				case "ProgramEligible_webActivityCode":
				case "Program_Eligible_GetAPI_MaxRedeem":

				case "ProgramEligible_earnPackCodeApi":
					resourceUrl = "/" + promo_env + "/" + program_version + "/" + "programInfo" + "/" + loyalty_env + "/"
							+ brandVal + "/" + "rewards" + "/" + code;
					break;
				case "earnPackCode_api_CodeRedeem":
				case "earnPackCode_api":

				case "earnPackCode_api_MaxRedeem":
					resourceUrl = "/" + loyalty_env + "/" + version + "/" + atc_vendor_cd + "/" + atc_channel_type_cd + "/"
							+ brandVal + "/" + "consumer" + "/" + code + "/" + "earn";
					break;

				case "Get_reward_Wishlist_API_Val":

				case "GetRewardCodeApi_EnrolledUser":
					resourceUrl = "/" + loyalty_env + "/" + version + "/" + atc_vendor_cd + "/" + atc_channel_type_cd + "/"
							+ brandVal + "/" + "consumer" + "/" + customerId + "/" + "rewards" + "/" + code;
					break;

				case "GET_reward_Category_api":
					resourceUrl = "/" + loyalty_env + "/" + version + "/" + atc_vendor_cd + "/" + atc_channel_type_cd + "/"
							+ brandVal + "/" + "reward" + "/" + "category";
					break;

				case "GetRewardCodeApi_UnenrolledUser":
					resourceUrl = "/" + loyalty_env + "/" + version + "/" + atc_vendor_cd + "/" + atc_channel_type_cd + "/"
							+ brandVal + "/" + "reward" + "/" + code;
					break;

				case "PostTokenApi":
					resourceUrl = "/" + bearerToken_code + "/" + "oauth2" + "/" + "token";
					break;

				case "deleteFromWishlist_Api":
					resourceUrl = "/" + loyalty_env + "/" + version + "/" + atc_vendor_cd + "/" + atc_channel_type_cd + "/"
							+ brandVal + "/" + "consumer" + "/" + customerId + "/" + "wishlist" + "/" + code;
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resourceUrl;
	}
}
