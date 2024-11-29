import SwiftUI

// Definición de colores
let primaryColor = Color(red: 0/255.0, green: 0/255.0, blue: 0/255.0, opacity: 1) // Negro
let secondaryColor = Color(red: 255/255.0, green: 255/255.0, blue: 255/255.0, opacity: 1) // Blanco

// Definición de fuentes
let primaryFont = Font.custom("Avenir-Roman", size: 20)

// Vista Principal
struct LayerView: View {
    var body: some View {
        ZStack {
            primaryColor // Fondo de la vista
                .ignoresSafeArea()

            VStack {
                TextView() // Usa la vista de texto personalizada
            }
        }
        .frame(width: 414, height: 896) // Tamaño de la vista
    }
}

// Vista de Texto
struct TextView: View {
    var body: some View {
        Text("Let’s get started")
            .foregroundColor(secondaryColor) // Color de texto
            .font(primaryFont) // Fuente personalizada
            .frame(width: 200, height: 50) // Ajusta el tamaño
            .background(primaryColor) // Fondo del texto
            .cornerRadius(10) // Bordes redondeados (opcional)
    }
}

// Vista de Previsualización
struct LayerView_Previews: PreviewProvider {
    static var previews: some View {
        LayerView()
    }
}
