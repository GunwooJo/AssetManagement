import * as React from 'react';
import { View } from 'react-native';
import { Button, Card, Icon, TouchableRipple } from 'react-native-paper';
import arrow_fw from '../assets/arrow_fw.svg'

const Section = ({data, navigation, type}) => (
  <View style={{ margin: 20, marginBottom: 0 }}>
    <Card mode={type}
    style={{ backgroundColor: "#FFFFFF", paddingTop: 10, paddingBottom: 10, borderWidth: 0.3 }}>
      {Object.values(data).map((v) => {
        if(v.type == "head") {
          if(v.button == true) {
            return (
              <TouchableRipple key={v.id}
              onPress={() => {navigation.navigate(v.stackPage)}}>
                <Card.Title key={v.id} title={v.mainText} subtitle={v.subText}
                titleVariant='headlineLarge' subtitleVariant='headlineSmall'
                right={() => <Button icon={arrow_fw}/>}/>
              </TouchableRipple>
            )
          }
          else {
            return (
              <Card.Title key={v.id} title={v.mainText} subtitle={v.subText}
              titleVariant='headlineLarge' subtitleVariant='headlineSmall'/>
            )
          }
        }
        else if(v.type == 'body') {
          if(v.button == true) {
            return (
              <TouchableRipple key={v.id}
              onPress={() => {navigation.navigate(v.stackPage)}}>
                <Card.Title key={v.id} title={v.mainText} subtitle={v.subText}
                titleVariant='titleLarge' subtitleVariant='titleMedium'
                left={p => <Icon {...p} source={v.image}/>}
                right={() => <Button icon={arrow_fw}/>}/>
              </TouchableRipple>
            )
          }
          else {
            return (
              <Card.Title key={v.id} title={v.mainText} subtitle={v.subText}
              titleVariant='titleLarge' subtitleVariant='titleMedium'
              left={p => <Icon {...p} source={v.image}/>}/>
            )
          }
        }
        else {
          if(v.button == true) {
            return (
              <TouchableRipple key={v.id}
              onPress={() => {navigation.navigate(v.stackPage)}}>
                <Card.Title key={v.id} title={v.mainText}
                titleVariant='headlineMedium'
                right={() => <Button icon={arrow_fw}/>}/>
              </TouchableRipple>
            )
          }
          else {
            return (
              <Card.Title key={v.id} title={v.mainText}
              titleVariant='headlineMedium'/>
            )
          }
        }
      })}
    </Card>
  </View>
);

export default Section;