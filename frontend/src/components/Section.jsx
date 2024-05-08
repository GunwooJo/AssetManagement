import * as React from 'react';
import { View } from 'react-native';
import { Button, Card, Icon, TouchableRipple } from 'react-native-paper';
//TouchableRipple = onPress 가능
function RightContent(btn, navigation, page) {
  return (<Button icon="image" onPress={() => {navigation.navigate(page)}}/>);
}

const Section = ({data, navigation, type}) => (
  <View style={{ margin: 20 }}>
    <Card mode={type}
    style={{ backgroundColor: "#FFFFFF", paddingTop: 10, paddingBottom: 10 }}>
      {Object.values(data).map((v) => {
        if (v.type == "head") {
          if (v.button == true) {
            return (
              <TouchableRipple key={v.id}
              onPress={() => {navigation.navigate(v.stackPage)}}>
                <Card.Title key={v.id} title={v.mainText} subtitle={v.subText}
                titleVariant='headlineLarge' subtitleVariant='headlineSmall'
                right={() => <Button icon="image"/>}/>
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
        else if (v.type == 'body') {
          if (v.button == true) {
            return (
              <TouchableRipple key={v.id}
              onPress={() => {navigation.navigate(v.stackPage)}}>
                <Card.Title key={v.id} title={v.mainText} subtitle={v.subText}
                titleVariant='titleLarge' subtitleVariant='titleMedium'
                left={p => <Icon source={require('../assets/kb_icon.png')}/>}
                
                right={() => <Button icon="image"/>}/>
              </TouchableRipple>
            )
          }
          else {
            return (
              <Card.Title key={v.id} title={v.mainText} subtitle={v.subText}
              titleVariant='titleLarge' subtitleVariant='titleMedium'
              left={p => <Icon {...p} source='camera'/>}/>
            )
          }
        }
        else {
          if (v.button == true) {
            return (
              <TouchableRipple key={v.id}
              onPress={() => {navigation.navigate(v.stackPage)}}>
                <Card.Title key={v.id} title={v.mainText}
                titleVariant='headlineMedium'
                right={() => <Button icon="image"/>}/>
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