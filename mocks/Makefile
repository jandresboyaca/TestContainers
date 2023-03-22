TAG=deploysoft/mockserver

publish: tag push

run:
	docker run --rm -p 3030:3030 $(TAG)
tag:
	docker build  -t $(TAG) .
push:
	docker push $(TAG)
