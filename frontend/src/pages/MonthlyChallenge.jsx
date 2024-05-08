import React from "react";
import { View, Text } from 'react-native';

export default function MonthlyChallenge() {
    return (
      <View style={{ alignSelf: "center", marginTop: 15 }}>
        <View>
          <Text>한 달 예산 지정</Text>
          <Text>X,XXX,XXX 원</Text>
        </View>
        <View style={{ flexDirection: 'column', alignItems: "center"}}>
          <Text>2024.05</Text>
          <Text>도전중</Text>
        </View>
      </View>
    );
}