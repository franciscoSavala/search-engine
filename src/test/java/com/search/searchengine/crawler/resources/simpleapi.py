from flask import Flask

app = Flask(__name__)

@app.route('/')
def hello_world():
    return "<b style='color: red;'> HOLAAAAA! </b>"

if __name__ == '__main__':
    app.run()