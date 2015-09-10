package android;

import android.util.Log;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.jianzhi_inc.dandelion.wechat.WeChat;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e("WXShare", "entry activity oncreate called");
        super.onCreate(savedInstanceState);

        if (WeChat.api == null) {
            Log.e("WXShare", "========Wechat.wxAPI is null!!!!");
            startMainActivity();
        } else {
            Log.e("WXShare", "========Wechat.wxAPI is not null, start to handleIntent----");
            WeChat.api.handleIntent(getIntent(), this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("WXShare", "entry activity onNewIntent called");
        if (WeChat.api == null) {
            Log.e("WXShare", "========Wechat.wxAPI is null!!!!");
            startMainActivity();
        } else {
            Log.e("WXShare", "========Wechat.wxAPI is not null, start to handleIntent----");
            WeChat.api.handleIntent(getIntent(), this);
        }
    }

    protected void startMainActivity() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage(getApplicationContext().getPackageName());
        getApplicationContext().startActivity(intent);
    }

    @Override
    public void onReq(BaseReq req) {
        // not implemented
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                WeChat.currentCallbackContext.success();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                WeChat.currentCallbackContext.error(WeChat.ERR_USER_CANCEL);
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                WeChat.currentCallbackContext.error(WeChat.ERR_AUTH_DENIED);
                break;
            case BaseResp.ErrCode.ERR_SENT_FAILED:
                WeChat.currentCallbackContext.error(WeChat.ERR_SENT_FAILED);
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                WeChat.currentCallbackContext.error(WeChat.ERR_UNSUPPORT);
                break;
            case BaseResp.ErrCode.ERR_COMM:
                WeChat.currentCallbackContext.error(WeChat.ERR_COMM);
                break;
            default:
                WeChat.currentCallbackContext.error(WeChat.ERR_UNKNOWN);
                break;
        }
        finish();
    }

}