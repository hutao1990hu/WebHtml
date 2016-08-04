package me.wjh.android.htmlui;

import java.util.List;

import me.wjh.android.htmlui.domain.Contact;
import me.wjh.android.htmlui.service.ContactService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class MainActivity extends Activity {
	private final static String TAG = "HtmlUIMainActivity";
	private WebView webView;
	private ContactService contactService;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        contactService = new ContactService();
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new ContactsPlugin(), "contactsAction");
        webView.loadUrl("file:///android_asset/index.html");
    }
    
    /**
     * 这个类提供出了一个视图和业务层通信的接口。HTML 中，通过这个类的实例，间接与业务 Bean 通信。
     * 为什么不直接将业务类提供给  webView, 让  HTML 中直接访问到这个类。而多出这样 "插件"~~
     * 我想：目前的这样一种架构，Activity 甚至有些类似于控制器的概念了。有点像 struts 中的 Action。
     * 在使用了 struts 框架的项目架构中，Action 也是被划分到视图层的。它和JSP页面共同完成准备数据和页面跳转的工作。
     * 因此，这里我们也不应该让 HTML 中的 JS 直接与业务层耦合。实现表现层和业务层的解耦
     */
    private class ContactsPlugin {
    	/**
    	 * 此方法将执行 JS 代码，调用 JS 函数：show()
    	 * 实现，将联系人信息展示到 HTML 页面上
    	 */
    	@SuppressWarnings("unused")
		public void getContacts() {
    		List<Contact> contacts =contactService.getContacts();
    		try {
				JSONArray array  = new JSONArray();
				for(Contact contact : contacts) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("id", contact.getId());
					jsonObject.put("mobile", contact.getMobile());
					jsonObject.put("name", contact.getName());
					array.put(jsonObject);
				}
				String json = array.toString();
				webView.loadUrl("javascript:show('"+ json +"')");
			} catch (JSONException e) {
				Log.i(TAG, e.toString());
			}
    	}
    	/**
    	 * 拨号
    	 */
    	@SuppressWarnings("unused")
		public void call(String phoneCode) {
    		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneCode));
    		startActivity(intent);
    	}
    }
}