package options;

import java.util.HashMap;
import java.util.Map;

public class ApiUrl {
    public static String URL = "https://staging.holytransaction.com";
    public static Map<String, String> paths = new HashMap<>();
    public static final String APIVERSION = "/api/v1/";

    public ApiUrl() {
        setPaths();
    }

    public ApiUrl(String url) {
        this.URL = url;
        setPaths();
    }

    private void setPaths() {
        paths.put("balances", URL + APIVERSION + "balances");
        paths.put("accounts/is_email_used", URL + APIVERSION + "accounts/is_email_used");
        paths.put("accounts", URL + APIVERSION + "accounts");
        paths.put("data/exchange_rates", URL + APIVERSION + "data/exchange_rates");
        paths.put("data/reserves", URL + APIVERSION + "data/reserves");
        paths.put("exchange_orders", URL + APIVERSION + "exchange_orders");
        paths.put("invoices", URL + APIVERSION + "invoices");
        paths.put("limits", URL + APIVERSION + "limits");
        paths.put("transactions", URL + APIVERSION + "transactions");
    }
}
