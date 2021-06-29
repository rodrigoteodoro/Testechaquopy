"""
https://www.ericthecoder.com/2021/02/15/chaquopy-sqlite3-tutorial-android-python-tutorial/
https://towardsdatascience.com/android-app-for-notion-automation-using-chaquopy-863e72fa4ecd

"""
import json

def desconto(x):
    return x - 10

def preco(x):
    y = gui_getdesconto(2)
    return x - y

def valorTotal(x):
    print("valorTotal")
    print(type(x))
    print(x)
    y = json.loads(x)
    itt = y.get("pedido", {}).get("itens", [])
    retorno = 0
    for item  in itt:
        print(item)
        retorno += item.get("valor", 0)
    # return y.get("pedido", {}).get("valor", 0)
    return retorno
