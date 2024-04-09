import * as React from 'react';
import { ScrollView, View } from 'react-native';
import Section from '../components/Section';
import { AccountListData } from '../utils/SampleData';

export default function AccountList({navigation}) {
  return(
    <View>
      <ScrollView>
      <Section data={AccountListData} navigation={navigation}/>
      </ScrollView>
    </View>
  );
}