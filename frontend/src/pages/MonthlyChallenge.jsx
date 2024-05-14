import React from "react";
import { View } from 'react-native';
import { Text } from "react-native-paper";
import Section from "../components/Section";
import { ChallengeData1, ChallengeData2, ChallengeData3 } from "../utils/SampleData";

export default function MonthlyChallenge() {
  return (
    <View>
      <View style={{ alignSelf: "center", marginTop: 15 }}>
        <Text variant='headlineLarge'>한 달 예산 지정</Text>
      </View>
      <Section data={ChallengeData1}/>
      <Section data={ChallengeData2}/>
      <Section data={ChallengeData3}/>
    </View>
  );   
}