import * as React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import Home from './src/pages/Home';
import Asset from './src/pages/Asset';
import ConversationalAI from './src/pages/ConversationalAI';
import Login from './src/pages/Login';
import Register from './src/pages/Register';
import AccountList from './src/pages/AccountList';
import ExpenseList from './src/pages/ExpenseList';
import AccountRegister from './src/pages/AccountRegister';
import AccountRegisterDetail from './src/pages/AccountRegisterDetail';
import DepositAndWithdraw from './src/pages/DepositAndWithdraw';
import Category from './src/pages/Category';
import MonthlyChallenge from './src/pages/MonthlyChallenge';

const HomeStack = createStackNavigator();
const AccountBookTobTab = createMaterialTopTabNavigator();
const Tab = createBottomTabNavigator();
const Stack = createStackNavigator();
var isLogin = true;

function HomeTab() {
  return(
    <HomeStack.Navigator>
      <HomeStack.Screen name="HomeTab" component={Home} options={{ title: '' }}/>
      <HomeStack.Screen name="AccountList" component={AccountList} options={{ title: '' }}/>
      <HomeStack.Screen name="ExpenseList" component={ExpenseList} options={{ title: '' }}/>
      <HomeStack.Screen name="AccountRegister" component={AccountRegister} options={{ title: '' }}/>
      <HomeStack.Screen name="국민은행(KB)" component={AccountRegisterDetail} options={{ title: '' }}/>
      <HomeStack.Screen name="농협은행(NH)" component={AccountRegisterDetail} options={{ title: '' }}/>
      <HomeStack.Screen name="하나은행" component={AccountRegisterDetail} options={{ title: '' }}/>
      <HomeStack.Screen name="나무(NH투자증권)" component={AccountRegisterDetail} options={{ title: '' }}/>
      <HomeStack.Screen name="미래에셋증권" component={AccountRegisterDetail} options={{ title: '' }}/>
      <HomeStack.Screen name="삼성증권" component={AccountRegisterDetail} options={{ title: '' }}/>
    </HomeStack.Navigator>
  )
}

function AccountBookTab() {
  return(
    <AccountBookTobTab.Navigator>
      <AccountBookTobTab.Screen name="입출금 내역" component={DepositAndWithdraw}/>
      <AccountBookTobTab.Screen name="카테고리" component={Category}/>
      <AccountBookTobTab.Screen name="챌린지" component={MonthlyChallenge}/>
    </AccountBookTobTab.Navigator>
  )
}

export default function App() {
  return (
    <NavigationContainer>
      {isLogin ?
        <Tab.Navigator>
          <Tab.Screen name="Home" component={HomeTab} options={{ title: '', headerShown: false }}/>
          <Tab.Screen name="My Asset" component={Asset} options={{ title: '' }}/>
          <Tab.Screen name="Account Book" component={AccountBookTab} options={{ title: '' }}/>
          <Tab.Screen name="Chat to AssetManager" component={ConversationalAI} options={{ title: '' }}/>
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