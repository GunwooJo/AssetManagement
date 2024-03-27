import * as React from 'react';
import { NavigationContainer } from '@react-navigation/native';
//import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import Home from './src/pages/Home';
import Asset from './src/pages/Asset';
import AccountBook from './src/pages/AccountBook';
import ConversationalAI from './src/pages/ConversationalAI';

//var Stack = createNativeStackNavigator();
const Tab = createBottomTabNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Tab.Navigator>
        <Tab.Screen name="Home" component={Home} />
        <Tab.Screen name="My Asset" component={Asset} />
        <Tab.Screen name="Account Book" component={AccountBook} />
        <Tab.Screen name="Chat to AssetManager" component={ConversationalAI} />
      </Tab.Navigator>
    </NavigationContainer>
  );
}