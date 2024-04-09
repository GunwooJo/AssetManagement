import * as React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import Home from './src/pages/Home';
import Asset from './src/pages/Asset';
import AccountBook from './src/pages/AccountBook';
import ConversationalAI from './src/pages/ConversationalAI';
import Login from './src/pages/Login';
import Register from './src/pages/Register';
import AccountList from './src/pages/AccountList';
import ExpenseList from './src/pages/ExpenseList';
import AccountRegister from './src/pages/AccountRegister';

const Stack = createNativeStackNavigator();
const HomeStack = createNativeStackNavigator();
const Tab = createBottomTabNavigator();
var isLogin = true;

function HomeTab() {
  return(
    <HomeStack.Navigator screenOptions={{ headerShown: true }}>
      <HomeStack.Screen name="HomeTab" component={Home}/>
      <HomeStack.Screen name="AccountList" component={AccountList}/>
      <HomeStack.Screen name="ExpenseList" component={ExpenseList}/>
      <HomeStack.Screen name="AccountRegister" component={AccountRegister}/>
    </HomeStack.Navigator>
  )
}

export default function App() {
  return (
    <NavigationContainer>
      {isLogin ?
        <Tab.Navigator>
          <Tab.Screen name="Home" component={HomeTab} />
          <Tab.Screen name="My Asset" component={Asset} />
          <Tab.Screen name="Account Book" component={AccountBook} />
          <Tab.Screen name="Chat to AssetManager" component={ConversationalAI} />
        </Tab.Navigator>
        :
        <Stack.Navigator>
          <Stack.Screen name='Login' component={Login}/>
          <Stack.Screen name='Register' component={Register}/>
        </Stack.Navigator>
      }
    </NavigationContainer>
  );
}