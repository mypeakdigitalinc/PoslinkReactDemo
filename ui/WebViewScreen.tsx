import { Platform, ScrollView, StyleSheet, Text, View } from "react-native";
import WebView from "react-native-webview";
import UIButton from "../components/UIButton";
import {NativeModules} from 'react-native';
import { useRef, useState } from "react";

const {PoslinkModule} = NativeModules;
const htmlPath = Platform.OS === 'android' ? 'file:///android_asset/transactionWebPage.html' : 'transactionWebPage.html';
const WebViewScreen = ({navigation}:{navigation:any}) => {
    const webViewRef = useRef<WebView | null>(null);
    const [logInfo, setLogInfo] = useState('LOG');
    const onMessage = async (data:any) =>{
        setLogInfo(data);
        await PoslinkModule?.showToast("Message Received: " + data);
        await PoslinkModule?.initTerminal();
        const trans = await PoslinkModule?.startTransaction(data);
        sendMsgToWebPage(trans)
    }
    const sendMsgToWebPage = (msg:any) => {
        if (webViewRef?.current) {
          webViewRef?.current?.postMessage(msg);
        }
    };
    return (
        <View style={styles.container}>
            <WebView ref={webViewRef}
                source={{ uri: htmlPath }} 
                style={{ flex: 1 }} 
                onMessage={(e) => {
                    const {data} = e.nativeEvent;
                    onMessage(data);
                }} 
            />
        </View>
    ); 
}


const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: '#fff',
      alignItems: 'stretch',
      justifyContent: 'center',
    },
});
export default WebViewScreen