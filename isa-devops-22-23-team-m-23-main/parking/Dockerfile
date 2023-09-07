# Use an official Python runtime as a parent image
FROM python:3

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Install any needed packages specified in requirements.txt
RUN pip install -r requirements.txt

# Expose port 9191 for the Flask app
EXPOSE 9191

# Define the command to run the Flask app
CMD ["python", "parking.py"]