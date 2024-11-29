import SwiftUI

struct SelectTypeView: View {
    var body: some View {
        VStack {
            Image("seller")
                .resizable()
                .frame(width: 100, height: 100)

            Button("Seller Type") {
                // Acción cuando se presiona el botón de vendedor
            }
            .buttonStyle(.borderedProminent)
            .padding()

            Image("client")
                .resizable()
                .frame(width: 100, height: 100)

            Button("Client Type") {
                // Acción cuando se presiona el botón de cliente
            }
            .buttonStyle(.borderedProminent)
            .padding()
        }
        .background(Color.black)
    }
}