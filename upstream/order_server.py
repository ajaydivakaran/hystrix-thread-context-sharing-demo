from flask import Flask
from flask import request
from flask.ext.json import FlaskJSON, json_response

app = Flask(__name__)
FlaskJSON(app)


@app.route('/orders')
def get_orders():
    correlation_id = request.headers.get('x-correlation-id') if request.headers.get('x-correlation-id') else "Dummy"
    dummy_orders = [dict(id=1, description="Order1"), dict(id=2, description="Order2")]
    return json_response(orders=dummy_orders, correlationId=correlation_id)


if __name__ == '__main__':
    app.run()
