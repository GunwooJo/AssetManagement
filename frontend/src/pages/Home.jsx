import * as React from 'react';
import { ScrollView, Text, View } from 'react-native';
import Section from '../components/Section';

const data1 = {
  1: ['head', false, '나의 자산', 'XXX,XXX,XXX 원'],
  2: ['body', false, 'A 은행', 'XXX,XXX,XXX 원'],
  3: ['body', false, 'A 은행', 'XXX,XXX,XXX 원'],
  4: ['body', false, 'A 은행', 'XXX,XXX,XXX 원'],
  5: ['foot', true, '모두 보기']
}

const data2 = {
  1: ['head', true, '이번 달 지출', 'YYY,YYY 원'],
  2: ['body', false, '오늘 지출', 'YY,YYY 원'],
  3: ['body', false, '고정 지출 (x건)', 'YYY,YYY 원']
}

export default function Home() {
  return (
    <View>
      <ScrollView>
        <Section data={data1}/>
        <Section data={data2}/>
      </ScrollView>
    </View>
  );
}

/*
타입으로 비교
head = [타입, 버튼 유무, 타이틀, 컨텐츠]
body = [타입, 버튼 유무, 타이틀, 컨텐츠, 이미지]
foot = [타입, 버튼 유무, 타이틀, 이미지(빈 이미지 = 정렬)]
*/