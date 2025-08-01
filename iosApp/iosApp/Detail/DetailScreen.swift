//
//  DetailScreen.swift
//  iosApp
//
//  Created by Nguyen TraVi on 8/1/25.
//
import SwiftUI

struct DetailScreen: View {
    
    var body: some View {
        ScrollView{
            
            VStack{
                
                ZStack{
                }
                .frame(maxWidth: .infinity, minHeight: 300, maxHeight: 300)
                
                VStack(alignment:.leading, spacing: 12){
                        
                    Button(action: {}){
                        HStack{
                            Image(systemName: "play.fill")
                                .foregroundColor(.black)
                            
                            Text("Start watching now")
                                .foregroundColor(.black)
                        }
                    }
                    .frame(maxWidth: .infinity, maxHeight: 40)
                    .padding()
                    .background(.red)
                    .clipShape(RoundedRectangle(cornerRadius: 8))
                    
                }
                .padding(20)
                .background(.black)
                
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            
        }
    }
}
