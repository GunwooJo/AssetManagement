import * as React from 'react';
import { View } from 'react-native';
import Section from '../components/Section';
import { ExpenseListData } from '../utils/SampleData';

export default function ExpenseList() {
  return(
    <View>
      <Section data={ExpenseListData}/>
    </View>
  );
}