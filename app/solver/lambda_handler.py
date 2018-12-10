from solver import solver

def request_handler(json_input, context):
    return solver(json_input)

