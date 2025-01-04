import * as React from 'react';
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import HomeScreen from './ui/HomeScreen';
import WebViewScreen from './ui/WebViewScreen';
import PaymentScreen from './ui/PaymentScreen';
import ProductListScreen from './ui/ProductListScreen';

const Stack = createNativeStackNavigator();
const App = () => {
  return (
    <NavigationContainer>
     <Stack.Navigator>
        <Stack.Screen
          name="Home"
          component={HomeScreen}
          options={{title: 'React Native Demo'}}
        />
        <Stack.Screen name="WebView" component={WebViewScreen} options={{title: 'WebView Demo'}}></Stack.Screen>
        <Stack.Screen name="ProductList" component={ProductListScreen} />
        <Stack.Screen name="Payment" component={PaymentScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default App;