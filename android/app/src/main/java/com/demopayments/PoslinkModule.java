package com.demopayments;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.pax.poscore.commsetting.AidlSetting;
import com.pax.poscore.commsetting.CommunicationSetting;
import com.pax.poslinkadmin.ExecutionResult;
import com.pax.poslinkadmin.constant.EdcType;
import com.pax.poslinkadmin.constant.PinpadEnableFlag;
import com.pax.poslinkadmin.constant.TransactionType;
import com.pax.poslinkadmin.internal.util.PoslinkGson;
import com.pax.poslinkadmin.manage.InitResponse;
import com.pax.poslinkadmin.manage.InputAccountRequest;
import com.pax.poslinkadmin.manage.InputAccountResponse;
import com.pax.poslinkadmin.payload.PayloadRequest;
import com.pax.poslinkadmin.util.AmountRequest;
import com.pax.poslinksemiintegration.POSLinkSemi;
import com.pax.poslinksemiintegration.Terminal;
import com.pax.poslinksemiintegration.transaction.DoCreditRequest;
import com.pax.poslinksemiintegration.transaction.DoCreditResponse;
import com.pax.poslinksemiintegration.util.TraceRequest;

public class PoslinkModule extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;
    public PoslinkModule(@Nullable ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }
    @NonNull
    @Override
    public String getName() {
        return "PoslinkModule";
    }

    @ReactMethod
    public void showToast(String message, Promise promise)
    {
        try {
            int eventId = 1; // This can be the return of the POSLINK
            Toast.makeText(reactContext, message, Toast.LENGTH_SHORT).show();
            promise.resolve(eventId);
        } catch(Exception e) {
            promise.reject("Create Event Error", e);
        }

    }

    @ReactMethod
    public void initTerminal(Promise promise){
        try
        {
            CommunicationSetting setting = ParameterManager.getInstance(reactContext).getCommSetting();
            Terminal terminal = POSLinkSemi.getInstance().getTerminal(reactContext, setting);
            if (terminal != null) {
                ExecutionResult<InitResponse> result = terminal.getManage().init();
                if (result.isSuccessful()) {
                    StringBuilder messageBuilder = new StringBuilder("Init Success!\n");
                    InitResponse response = result.response();
                    messageBuilder.append("AppName: ").append(response.appName()).append("\n")
                            .append("AppVersion: ").append(response.appVersion()).append("\n")
                            .append("SN: ").append(response.sn()).append("\n")
                            .append("ModelName: ").append(response.modelName()).append("\n")
                            .append("OSVersion: ").append(response.osVersion());
                    promise.resolve(messageBuilder.toString());
                } else {
                    promise.reject(new Exception("Trans Failed!\n" + "Error Message:" + result.message()));
                }
            } else {
                promise.reject(new Exception("Init Failed!"));
            }
        }catch (Exception e){
            promise.reject("Init Terminal Error", e);
        }
    }

    @ReactMethod
    public void startTransaction(String strAmount, Promise promise){
        try{

            CommunicationSetting setting = ParameterManager.getInstance(reactContext).getCommSetting();
            Terminal terminal = POSLinkSemi.getInstance().getTerminal(reactContext, setting);
            if (terminal != null) {
                AmountRequest amountRequest = new AmountRequest();
                amountRequest.setTransactionAmount(strAmount);
                DoCreditRequest doCreditRequest = new DoCreditRequest();
                doCreditRequest.setTransactionType(TransactionType.SALE);
                doCreditRequest.setAmountInformation(amountRequest);
                ExecutionResult<DoCreditResponse> executionResult = terminal.getTransaction().doCredit(doCreditRequest);
                if (executionResult.isSuccessful() && executionResult.response() != null) {
                    String payloadResp = PoslinkGson.getGson().toJson(executionResult.response());
                    promise.resolve(payloadResp);
                } else {
                    promise.resolve("{\"error\": \"Unable to start transaction\"}");
                }
            }
        }
        catch (Exception e){
            promise.reject("{\"error\": \"Start Transaction Error: "+ e.getMessage() +"\"}");
        }
    }

    @ReactMethod
    public void startSaleTransaction(String strAmount, Promise promise){
        try{

            CommunicationSetting setting = ParameterManager.getInstance(reactContext).getCommSetting();
            Terminal terminal = POSLinkSemi.getInstance().getTerminal(reactContext, setting);
            if (terminal != null) {
                AmountRequest amountRequest = new AmountRequest();
                amountRequest.setTransactionAmount(strAmount);
                DoCreditRequest doCreditRequest = new DoCreditRequest();
                doCreditRequest.setTransactionType(TransactionType.SALE);
                doCreditRequest.setAmountInformation(amountRequest);
                ExecutionResult<DoCreditResponse> executionResult = terminal.getTransaction().doCredit(doCreditRequest);
                if (executionResult.isSuccessful() && executionResult.response() != null) {
                    String payloadResp = PoslinkGson.getGson().toJson(executionResult.response());
                    promise.resolve(payloadResp);
                } else {
                    promise.resolve("{\"error\": \"Unable to start transaction\"}");
                }
            }
        }
        catch (Exception e){
            promise.reject("{\"error\": \"Start Transaction Error: "+ e.getMessage() +"\"}");
        }
    }

    @ReactMethod
    public void startRefundTransaction(String strAmount, Promise promise){
        try{
            CommunicationSetting setting = ParameterManager.getInstance(reactContext).getCommSetting();
            Terminal terminal = POSLinkSemi.getInstance().getTerminal(reactContext, setting);
            if (terminal != null) {
                AmountRequest amountRequest = new AmountRequest();
                amountRequest.setTransactionAmount(strAmount);
                DoCreditRequest doCreditRequest = new DoCreditRequest();
                doCreditRequest.setTransactionType(TransactionType.RETURN);
                doCreditRequest.setAmountInformation(amountRequest);
                ExecutionResult<DoCreditResponse> executionResult = terminal.getTransaction().doCredit(doCreditRequest);
                if (executionResult.isSuccessful() && executionResult.response() != null) {
                    String payloadResp = PoslinkGson.getGson().toJson(executionResult.response());
                    promise.resolve(payloadResp);
                } else {
                    promise.resolve("{\"error\": \"Unable to start transaction\"}");
                }
            }
        }
        catch (Exception e){
            promise.reject("{\"error\": \"Start Transaction Error: "+ e.getMessage() +"\"}");
        }
    }

    @ReactMethod
    public void startVoidTransaction(String traceId, String strAmount, Promise promise){
        try{
            CommunicationSetting setting = ParameterManager.getInstance(reactContext).getCommSetting();
            Terminal terminal = POSLinkSemi.getInstance().getTerminal(reactContext, setting);
            if (terminal != null) {
                AmountRequest amountRequest = new AmountRequest();
                amountRequest.setTransactionAmount(strAmount);
                DoCreditRequest doCreditRequest = new DoCreditRequest();
                doCreditRequest.setTransactionType(TransactionType.VOID);
                //doCreditRequest.setAmountInformation(amountRequest);
                doCreditRequest.setTraceInformation(TraceRequest.builder().originalTransactionIdentifier(traceId).build());
                ExecutionResult<DoCreditResponse> executionResult = terminal.getTransaction().doCredit(doCreditRequest);
                if (executionResult.isSuccessful() && executionResult.response() != null) {
                    String payloadResp = PoslinkGson.getGson().toJson(executionResult.response());
                    promise.resolve(payloadResp);
                } else {
                    promise.resolve("{\"error\": \"Unable to start transaction\"}");
                }
            }
        }
        catch (Exception e){
            promise.reject("{\"error\": \"Start Transaction Error: "+ e.getMessage() +"\"}");
        }
    }

    @ReactMethod
    public void startPreAuthTransaction(String strAmount, Promise promise){
        try{
            CommunicationSetting setting = ParameterManager.getInstance(reactContext).getCommSetting();
            Terminal terminal = POSLinkSemi.getInstance().getTerminal(reactContext, setting);
            if (terminal != null) {
                AmountRequest amountRequest = new AmountRequest();
                amountRequest.setTransactionAmount(strAmount);
                DoCreditRequest doCreditRequest = new DoCreditRequest();
                doCreditRequest.setTransactionType(TransactionType.AUTHORIZATION);
                doCreditRequest.setAmountInformation(amountRequest);
                ExecutionResult<DoCreditResponse> executionResult = terminal.getTransaction().doCredit(doCreditRequest);
                if (executionResult.isSuccessful() && executionResult.response() != null) {
                    String payloadResp = PoslinkGson.getGson().toJson(executionResult.response());
                    promise.resolve(payloadResp);
                } else {
                    promise.resolve("{\"error\": \"Unable to start transaction\"}");
                }
            }
        }
        catch (Exception e){
            promise.reject("{\"error\": \"Start Transaction Error: "+ e.getMessage() +"\"}");
        }
    }

    @ReactMethod
    public void startPostAuthTransaction(String traceId, String strAmount, Promise promise){
        try{
            CommunicationSetting setting = ParameterManager.getInstance(reactContext).getCommSetting();
            Terminal terminal = POSLinkSemi.getInstance().getTerminal(reactContext, setting);
            if (terminal != null) {
                AmountRequest amountRequest = new AmountRequest();
                amountRequest.setTransactionAmount(strAmount);
                DoCreditRequest doCreditRequest = new DoCreditRequest();
                doCreditRequest.setTransactionType(TransactionType.POST_AUTHORIZATION);
                doCreditRequest.setAmountInformation(amountRequest);
                doCreditRequest.setTraceInformation(TraceRequest.builder().originalTransactionIdentifier(traceId).build());
                ExecutionResult<DoCreditResponse> executionResult = terminal.getTransaction().doCredit(doCreditRequest);
                if (executionResult.isSuccessful() && executionResult.response() != null) {
                    String payloadResp = PoslinkGson.getGson().toJson(executionResult.response());
                    promise.resolve(payloadResp);
                } else {
                    promise.resolve("{\"error\": \"Unable to start transaction\"}");
                }
            }
        }
        catch (Exception e){
            promise.reject("{\"error\": \"Start Transaction Error: "+ e.getMessage() +"\"}");
        }
    }

    @ReactMethod
    public void readCardData(String strAmount, Promise promise){
        try{
            CommunicationSetting setting = ParameterManager.getInstance(reactContext).getCommSetting();
            Terminal terminal = POSLinkSemi.getInstance().getTerminal(reactContext, setting);
            if (terminal != null) {

                InputAccountRequest inputAccountRequest = new InputAccountRequest();
                inputAccountRequest.setTimeout("600");
                inputAccountRequest.setEdcType(EdcType.CREDIT);
                inputAccountRequest.setTransactionType(TransactionType.SALE);
                inputAccountRequest.setMagneticSwipePinpadEnableFlag(PinpadEnableFlag.ALLOWED);
                ExecutionResult<InputAccountResponse> inputAccountResponse = terminal.getManage().inputAccount(inputAccountRequest);
                if (inputAccountResponse.isSuccessful() && inputAccountResponse.response() != null) {
                    String payloadResp = PoslinkGson.getGson().toJson(inputAccountResponse.response());
                    promise.resolve(payloadResp);
                } else {
                    promise.resolve("{\"error\": \"Unable to start transaction\", \"msg\": \""+ inputAccountResponse.message() +"\"}");
                }
            }
        }
        catch (Exception e){
            promise.reject("{\"error\": \"Start Transaction Error: "+ e.getMessage() +"\"}");
        }
    }
}
