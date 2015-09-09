package android;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeChat.api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        WeChat.api.handleIntent(intent, this);
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