package com.unisys.trans.cps.middleware.constants;

public class CPSConstants {

	private CPSConstants() {
		// Private constructor to hide the implicit public one
		// This class should not be instantiated
		throw new AssertionError("Instantiation not allowed for this class.");
	}

	// ----------------------------------------------------------------------
	// These values are used by the server and client.
	// ----------------------------------------------------------------------
	/**
	 * This represents the name of the System Admininstrator role.
	 */
	public static final String CPS_ADMIN = "System Administrator";

	// ----------------------------------------------------------------------
	// Contants used to represent Host Communication Contexts
	// ----------------------------------------------------------------------
	public static final String TUXEDO = "TUX";
	public static final String HTTPXML = "HTT";
	public static final String EDI = "EDI";
	public static final String TTY = "TTY";

	public static final String DOT_OPERATOR = ".";
	public static final String CONNECTION_URL_PROP = "connectionURL";
	public static final String TOKEN_PROP = "Token";
	public static final String SOAP_ACTION = "soapAction";

	public static final String CARRIER_NAME_AC = "AC";
	public static final String CARRIER_NAME_NW = "NW";
	public static final String CARRIER_NAME_UA = "UA";
	public static final String CARRIER_NAME_KL = "KL";
	public static final String CARRIER_NAME_AA = "AA";
	public static final String CARRIER_NAME_SK = "SK";
	public static final String CARRIER_NAME_AF = "AF";
	public static final String CARRIER_NAME_NZ = "NZ";
	public static final String CARRIER_NAME_VS = "VS";
	public static final String CARRIER_NAME_UW = "UW";
	public static final String CARRIER_NAME_CA = "CA";
	public static final String CARRIER_NAME_AI = "AI";
	public static final String CARRIER_NAME_MH = "MH";
	public static final String CARRIER_NAME_AFKL = "AF-KL";

	// LIST OF CPS CARRIERS ACNs
	public static final String CARRIER_ACN_AC = "014";

	public static final String CARRIER_ACN_UA = "016";

	public static final String CARRIER_ACN_AA = "001";
	public static final String CARRIER_ACN_SK = "117";
	public static final String CARRIER_ACN_NZ = "086";
	public static final String CARRIER_ACN_VS = "932";
	public static final String CARRIER_ACN_UW = "777";
	public static final String CARRIER_ACN_AI = "098";
	public static final String CARRIER_ACN_MH = "232";
	public static final String CARRIER_ACN_AFKL = "";
	public static final String CARRIER_ACN_CA = "999";

	// Service name constants
	public static final String FLIGHTSTATUS_LMS = "FlightStatusLMS";
	public static final String FLIGHTSTATUS_NONLMS = "FlightStatusNonLMS";
	public static final String FLIGHTSCHDEULE_LMS = "FlightScheduleLMS";
	public static final String FLIGHTSCHEDULE_NONLMS = "FlightScheduleNonLms";
	public static final String FLIGHTSCHDEULE_AA = "FlightScheduleAA";
	
	public static final String USERID = "UserId";
	public static final String USERSTATION = "userStation";
	public static final String AGENT = "agent";
	
	//other microservice URL constants
	public static final String COMMON_SERVICE_URL = "Common_service_Url";

	public static final String ZERO_STRING = "0";
}