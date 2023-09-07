from flask import Flask, request, jsonify
from datetime import datetime, timedelta
import threading
import re
import time

class Park:
    def __init__(self, start, end, client_mail):
        self.start = start
        self.end = end
        self.client_mail = client_mail
        self.notified_once = False

    def to_str(self):
        return 'Start: {}, End: {}, Client mail: {}, Notified once: {}'.format(self.start, self.end, self.client_mail,
                                                                               self.notified_once)


regex_plaque_fr = r"^[A-Z]{2}-[0-9]{3}-[A-Z]{2}$"
magic_key = 'AZ'
parking_data = {}

app = Flask(__name__)

#print (parking_data)

@app.before_request
def before_request_func():
    if request.method == 'POST' and request.headers['Content-Type'] != 'application/json':
        return jsonify({'error': 'Content-Type must be application/json.'}), 400


@app.route('/start-park', methods=['POST'])
def start_parking():
    data = request.get_json()

    license_plate = data.get('licensePlate')
    client_phone = data.get('phoneNumber')

    if not license_plate:
        return jsonify({'message': 'License plate is required.'}), 400

    if not re.match(regex_plaque_fr, license_plate):
        return jsonify({'message': 'License plate is not valid.'}), 400

    if license_plate in parking_data:
        return jsonify({'message': 'License plate already exists.'}), 409

    if not magic_key in license_plate:
        return jsonify({'message': 'License plate is not allowed to park.'}), 403

    parking_data[license_plate] = Park(datetime.now(), datetime.now() + timedelta(minutes=30), client_phone)
    print('user {} started parking'.format(license_plate))
    return jsonify({'message': 'Parking started successfully.'}), 201


@app.route('/remaining-minutes/<license_plate>', methods=['GET'])
def get_remaining_minutes(license_plate):
    if license_plate in parking_data:
        park = parking_data[license_plate]
        remaining_min = int((park.end - datetime.now()).seconds / 60)
        print('Remaining minutes: {}'.format(remaining_min))
        return str(remaining_min), 200
    else:
        return jsonify({'error': 'No parking data found for this license plate.'}), 404


def delete_park(to_delete):
    for license_plate in to_delete:
        del parking_data[license_plate]
    to_delete.clear()


def handle_reminder_and_delete():
    while True:
        to_delete = []
        for license_plate, park in parking_data.items():
            remaining_time = int((park.end - datetime.now()).seconds / 60) + 1
            if 4 <= remaining_time <= 5 and not park.notified_once:
                message = f'Rappel: il reste {int(remaining_time)} minutes à votre place de parking (immatriculation: {license_plate}) '
                print(message)
                park.notified_once = True

            elif remaining_time <= 0 or remaining_time >= 1440:
                message = f'La place de parking (immatriculation: {license_plate}) a expiré'
                print(message)
                to_delete.append(license_plate)

        delete_park(to_delete)
        print('Parking data: {}'.format(parking_data))
        time.sleep(60)



if __name__ == '__main__':
    print('Starting parking app...')
    thread = threading.Thread(target=handle_reminder_and_delete)
    thread.start()
    app.run(debug=False, port=9191)