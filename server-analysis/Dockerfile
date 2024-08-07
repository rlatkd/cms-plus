FROM       python:3.6-slim
RUN        mkdir /app
WORKDIR    /app
ADD        . /app/
ENV        PYTHONUNBUFFERED 1
ENV        TZ=Asia/Seoul
ENV        LANG C.UTF-8
ENV        DEBIAN_FRONTEND=noninteractive 
ENV        PORT=8888
RUN        apt-get update && apt-get install -y --no-install-recommends \
               tzdata \
               python3-setuptools \
               python3-pip \
               python3-dev \
               python3-venv \
               git \
                && \
           apt-get clean && \
           rm -rf /var/lib/apt/lists/*
RUN        pip3 install --upgrade pip 
RUN        pip3 install pipenv
RUN        pip3 install --force-reinstall --no-deps nbconvert==6.0.7
RUN        pipenv install --skip-lock --system --dev

# Add the command to install requirements.txt
RUN        pip3 install -r requirements.txt

CMD        uvicorn src.server:app --host 0.0.0.0 --port ${PORT}
