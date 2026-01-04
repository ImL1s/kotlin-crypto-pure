print("Hello World")
try:
    import bip_utils
    print("bip_utils imported")
except ImportError as e:
    print(f"Import Error: {e}")
except Exception as e:
    print(f"Other Error: {e}")
