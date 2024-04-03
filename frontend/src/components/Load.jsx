import * as React from 'react';
import { View } from 'react-native';
import { ActivityIndicator, Text } from 'react-native-paper';

export default function Load() {
  const a = 123

  return (
    <View>
      <ActivityIndicator animating={true} size={100} style={{ marginTop: 100 }}/>
      <Text style={{ textAlign: 'center', marginTop: 40 }}>이게 명언</Text>
    </View>
  )
}