import * as React from 'react';
import { ScrollView, View } from 'react-native';
import Section from '../components/Section';
import { HomeData1, HomeData2 } from '../utils/SampleData';

export default function Home({ navigation }) {
  return (
    <View>
      <ScrollView>
      <Section data={HomeData1} navigation={navigation}/>
      <Section data={HomeData2} navigation={navigation}/>
      </ScrollView>
    </View>
  );
}