from flask import Flask
from flask import request
from flask.ext.json import FlaskJSON, json_response

app = Flask(__name__)
FlaskJSON(app)


@app.route('/payments')
def get_orders():
    correlation_id = request.headers.get('x-correlation-id') if request.headers.get('x-correlation-id') else "Dummy"
    dummy_payments = [dict(id=1, amount=20), dict(id=2, amount=30)]
    return json_response(payments=dummy_payments, correlationId=correlation_id)


if __name__ == '__main__':
    app.run(port=5001)
