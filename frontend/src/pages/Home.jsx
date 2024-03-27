import * as React from 'react';
import { Text, View } from 'react-native';
import Card from '../components/Card';

export default function Home() {
  return (
    <View style={{ flex: 1, padding: 15}}>
      <Card/>
      <Card/>
    </View>
  );
}

/*
타입으로 비교
head = [타입, 버튼 유무, 타이틀, 컨텐츠]
body = [타입, 버튼 유무, 타이틀, 컨텐츠, 이미지]
foot = [타입, 버튼 유무, 타이틀, 이미지(빈 이미지 = 정렬)]
*/