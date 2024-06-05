import * as React from 'react';
import { FlatList, RefreshControl, View } from 'react-native';
import Chat from "../components/Chat";
import { ConversationalAIData } from "../utils/SampleData";
import { TextInput } from 'react-native-paper';

export default function ConversationalAI() {
      const [refreshing, setRefreshing] = React.useState(false);
      const onRefresh = () => {
        setRefreshing(true);
        // 여기에 데이터를 다시 불러오는 작업을 수행합니다.
        // 데이터를 다시 불러온 후에 setRefreshing(false)를 호출하여 새로고침 상태를 해제합니다.
        setTimeout(() => {
          setRefreshing(false);
        }, 2000); // 예시로 2초 후에 새로고침 상태를 해제합니다.
      };
    return(
      <View style={{ flex: 1 }}>
        <FlatList
        data={[ConversationalAIData]}
        renderItem={({ item }) => <Chat data={item}/>}
        keyExtractor={(item, index) => index.toString()} // 각 섹션을 구분하기 위해 인덱스를 키로 사용
        refreshControl={<RefreshControl refreshing={refreshing} onRefresh={onRefresh} />}
        />
        <TextInput activeOutlineColor='black' selectionColor='black' style={{ backgroundColor: "#FFFFFF" }}/>
      </View>
    );
}