package me.wjh.android.htmlui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import me.wjh.android.htmlui.domain.Contact;
import me.wjh.android.htmlui.service.ContactService;

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


//		阿斯大三大四的
    }
    
    /**
     * ������ṩ����һ����ͼ��ҵ���ͨ�ŵĽӿڡ�HTML �У�ͨ��������ʵ������ҵ�� Bean ͨ�š�
     * Ϊʲô��ֱ�ӽ�ҵ�����ṩ��  webView, ��  HTML ��ֱ�ӷ��ʵ�����ࡣ�������� "���"~~
     * ���룺Ŀǰ������һ�ּܹ���Activity ������Щ�����ڿ������ĸ����ˡ��е��� struts �е� Action��
     * ��ʹ���� struts ��ܵ���Ŀ�ܹ��У�Action Ҳ�Ǳ����ֵ���ͼ��ġ����JSPҳ�湲ͬ���׼����ݺ�ҳ����ת�Ĺ�����
     * ��ˣ���������Ҳ��Ӧ���� HTML �е� JS ֱ����ҵ�����ϡ�ʵ�ֱ��ֲ��ҵ���Ľ���
     */
    private class ContactsPlugin {
    	/**
    	 * �˷�����ִ�� JS ���룬���� JS ����show()
    	 * ʵ�֣�����ϵ����Ϣչʾ�� HTML ҳ����
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
    	 * ����
    	 */
    	@SuppressWarnings("unused")
		public void call(String phoneCode) {
    		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneCode));
    		startActivity(intent);
    	}
    }
}