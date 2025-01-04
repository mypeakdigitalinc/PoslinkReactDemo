import React, { useState } from "react";
import { Button, Image, ScrollView, StatusBar, StyleSheet, Text, TextInput, View } from "react-native";
import UIButton from "../components/UIButton";
import {NativeModules} from 'react-native';
const {PoslinkModule} = NativeModules;


const HomeScreen = ({navigation}:{navigation:any}) => {
    const [terminalInfo, setTerminalInfo] = useState('INFO');
    const [txtAmount, onChangetxtAmount] = useState('1000');
    return (
        <View style={styles.container}>
          <Image width={200} height={267} source={require('.././assets/images/logo.png')} style={{width: 200, height: 150, alignSelf: "center"}} />
          <TextInput style={styles.textInput} placeholder='Amount' onChangeText={onChangetxtAmount} value={txtAmount} keyboardType="numeric" ></TextInput>
          <UIButton title="Start Transaction" onPress={async ()=>{
              await PoslinkModule?.showToast("Start Transaction");
              const initTrans = await PoslinkModule?.initTerminal();
              setTerminalInfo(initTrans);
              const trans = await PoslinkModule?.startTransaction(txtAmount);
              console.debug(trans);
              setTerminalInfo(trans);
              //navigation.navigate('Payment', {name: 'Jane'})
            }}>
          </UIButton>
          <UIButton title="WebView Demo" onPress={async ()=>{
              navigation.navigate('WebView')
            }}>
          </UIButton>          
          <ScrollView>
            <Text>{terminalInfo}</Text>
          </ScrollView>
        </View>
    );
  };
  const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: '#fff',
      alignItems: 'stretch',
      justifyContent: 'center',
    },
    textTitle: {
      flex: 1,
      marginTop: 10,
      fontSize: 200,
      borderColor: "#155CA2",
    },
    formContainer:{
      alignSelf: 'stretch',
      marginTop: 10
    },
    textInput:{
      justifyContent: "center",
      alignItems: "stretch",
 
      borderWidth:2,
      height: 50,
      borderColor: "#155CA2",
      borderRadius: 10,
      padding:5,
      margin:5
    },
    buttonSubmit:{
      padding:5,
      margin:5
    }
  });  
export default HomeScreen;