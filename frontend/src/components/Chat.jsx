import React from "react";
import { View } from 'react-native';
import { ActivityIndicator, Card, Text } from "react-native-paper";

export default function Chat({data}) {
    return(
    <View style={{ flex: 1 }}>
        {Object.values(data).map((v) => {
            if(v.type == "user") {
                if(v.isLoading == true) {
                    return(
                        <Card mode="outlined" key={v.id}
                        style={{ backgroundColor: "#FFFFFF", marginTop: 20,
                        marginRight: 20, marginLeft: "30%", alignSelf: "flex-end"}}>
                            <Card.Content>
                                <ActivityIndicator animating={true} color="#000000"/>
                            </Card.Content>
                        </Card>
                    );
                }
                else {
                    return(
                        <Card mode="outlined" key={v.id}
                        style={{ backgroundColor: "#FFFFFF", marginTop: 20,
                        marginRight: 20, marginLeft: "30%", alignSelf: "flex-end"}}>
                            <Card.Content>
                                <Text>{v.text}</Text>
                            </Card.Content>
                        </Card>
                    );
                }
            }
            else if(v.type == "bot") {
                if(v.isLoading == true) {
                    return(
                        <Card mode="outlined" key={v.id}
                        style={{ backgroundColor: "#FFFFFF", marginTop: 20,
                        marginRight: "30%", marginLeft: 20, alignSelf: "flex-start"}}>
                            <Card.Content>
                                <ActivityIndicator animating={true} color="#000000"/>
                            </Card.Content>
                        </Card>
                    );
                }
                else {
                    return(
                        <Card mode="outlined" key={v.id}
                        style={{ backgroundColor: "#FFFFFF", marginTop: 20,
                        marginRight: "30%", marginLeft: 20, alignSelf: "flex-start"}}>
                            <Card.Content>
                                <Text>{v.text}</Text>
                            </Card.Content>
                        </Card>
                    );
                }
            }
        })}
    </View>
    );
}