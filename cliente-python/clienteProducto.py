import datetime
import configparser
import requests
from requests.structures import CaseInsensitiveDict

# =========================
# CONFIGURACIÓN
# =========================
api_productos_url_base = None
archivo_config = "ConfigFile.properties"

config = configparser.RawConfigParser()
archivos = config.read(archivo_config)
print("Archivos leídos:", archivos)

def cargar_variables():
    global api_productos_url_base
    config = configparser.RawConfigParser()
    config.read(archivo_config)
    api_productos_url_base = config.get('SeccionApi', 'api_productos_url_base')
    print(f"Usando API base: {api_productos_url_base}")


# =========================
# FUNCIONES DE CONSULTA
# =========================
def listar():
    """GET /productos - Listar todos los productos"""
    headers = CaseInsensitiveDict()
    headers["Accept"] = "application/json"

    r = requests.get(f"{api_productos_url_base}", headers=headers)
    if r.status_code == 200:
        listado = r.json()
        print("\n📦 LISTADO DE PRODUCTOS:")
        for item in listado:
            print(f"  ID: {item['id']}, Nombre: {item['nombre']}, Stock: {item.get('stock', 'N/A')}")
    else:
        print("❌ Error", r.status_code, r.text)


def detalle(id: int):
    """GET /productos/{id} - Detalle de un producto"""
    headers = CaseInsensitiveDict()
    headers["Accept"] = "application/json"

    r = requests.get(f"{api_productos_url_base}/{id}", headers=headers)
    if r.status_code == 200:
        item = r.json()
        print(f"\n📋 DETALLE DEL PRODUCTO:\n  ID: {item['id']}\n  Nombre: {item['nombre']}\n  Stock: {item.get('stock', 'N/A')}")
    elif r.status_code == 404:
        print("⚠️ Producto no encontrado.")
    else:
        print("❌ Error", r.status_code, r.text)


def buscar(nombre: str):
    """GET /productos/buscar?nombre=Nombre - Buscar productos por nombre"""
    headers = CaseInsensitiveDict()
    headers["Accept"] = "application/json"

    r = requests.get(f"{api_productos_url_base}/buscar", headers=headers, params={'nombre': nombre})
    if r.status_code == 200:
        listado = r.json()
        print(f"\n🔍 Resultados de búsqueda para '{nombre}':")
        for item in listado:
            print(f"  ID: {item['id']}, Nombre: {item['nombre']}, Stock: {item.get('stock', 'N/A')}")
    elif r.status_code == 404:
        print("⚠️ No se encontraron productos con ese nombre.")
    else:
        print("❌ Error", r.status_code, r.text)


# =========================
# FUNCIONES DE MODIFICACIÓN
# =========================
def crear(id: int, nombre: str, stock: int = 0):
    """POST /productos/crear - Crear un nuevo producto"""
    headers = CaseInsensitiveDict()
    headers["Accept"] = "application/json"
    headers["Content-Type"] = "application/json"

    datos = {
        'id': id,
        'nombre': nombre,
        'stock': stock
    }

    r = requests.post(f"{api_productos_url_base}/crear", headers=headers, json=datos)
    if 200 <= r.status_code < 300:
        print("✅ Producto creado correctamente")
        print(r.text)
    else:
        print("❌ Error", r.status_code, r.text)


def actualizar(id: int, nombre: str = None, stock: str = None):
    """PUT /productos/{id} - Actualizar producto existente"""
    headers = CaseInsensitiveDict()
    headers["Accept"] = "application/json"
    headers["Content-Type"] = "application/json"

    datos = {}
    if nombre is not None and nombre.strip() != "":
        datos['nombre'] = nombre
    if stock is not None and stock.strip() != "":
        try:
            datos['stock'] = int(stock)
        except ValueError:
            print("⚠️ El stock debe ser un número.")
            return

    if not datos:
        print("ℹ️ No se ingresaron cambios. Nada que actualizar.")
        return

    r = requests.put(f"{api_productos_url_base}/{id}", headers=headers, json=datos)
    if 200 <= r.status_code < 300:
        print(f"✅ Producto {id} actualizado correctamente")
        print(r.text)
    elif r.status_code == 404:
        print("⚠️ Producto no encontrado.")
    else:
        print("❌ Error", r.status_code, r.text)



def eliminar(id: int):
    """DELETE /productos/{id} - Eliminar un producto"""
    headers = CaseInsensitiveDict()
    headers["Accept"] = "application/json"

    r = requests.delete(f"{api_productos_url_base}/{id}", headers=headers)
    if 200 <= r.status_code < 300:
        print(f"✅ Producto {id} eliminado correctamente")
    elif r.status_code == 404:
        print("⚠️ Producto no encontrado.")
    else:
        print("❌ Error", r.status_code, r.text)


# =========================
# MENÚ PRINCIPAL
# =========================
def menu():
    while True:
        print("""
===============================
     🧩 MENÚ DE PRODUCTOS
===============================
1. Listar productos
2. Ver detalle de un producto
3. Buscar producto por nombre
4. Crear producto
5. Actualizar producto
6. Eliminar producto
0. Salir
""")
        opcion = input("Seleccione una opción: ").strip()

        if opcion == "1":
            listar()

        elif opcion == "2":
            try:
                id = int(input("Ingrese el ID del producto: "))
                detalle(id)
            except ValueError:
                print("⚠️ ID inválido.")

        elif opcion == "3":
            nombre = input("Ingrese el nombre o palabra clave: ").strip()
            buscar(nombre)

        elif opcion == "4":
            try:
                id = int(input("Ingrese ID del nuevo producto: "))
                nombre = input("Ingrese nombre: ").strip()
                stock = int(input("Ingrese stock: "))
                crear(id, nombre, stock)
            except ValueError:
                print("⚠️ Datos inválidos.")

        elif opcion == "5":
            print("📝 Actualizar producto")
            try:
                id = int(input("Ingrese ID del producto a actualizar: "))
            except ValueError:
                print("⚠️ El ID debe ser un número.")
                continue

            nombre = input("Nuevo nombre (deje vacío si no quiere cambiarlo): ").strip()
            stock = input("Nuevo stock (deje vacío si no quiere cambiarlo): ").strip()

            # Si no ingresó valor, pasamos None para que la función no modifique ese campo
            nombre = nombre if nombre != "" else None
            stock = stock if stock != "" else None

            actualizar(id, nombre, stock)


        elif opcion == "6":
            try:
                id = int(input("Ingrese ID del producto a eliminar: "))
                eliminar(id)
            except ValueError:
                print("⚠️ ID inválido.")

        elif opcion == "0":
            print("👋 Saliendo del sistema...")
            break

        else:
            print("⚠️ Opción inválida. Intente de nuevo.")


# =========================
# PROCESO PRINCIPAL
# =========================

cargar_variables()
menu()
print("Finalizando " + datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
