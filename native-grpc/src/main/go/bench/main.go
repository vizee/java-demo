package main

import (
	"context"
	"encoding/hex"
	"flag"
	"fmt"
	"os"
	"sync"
	"time"

	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
)

type passthroughCodec struct{}

// Marshal implements encoding.Codec.
func (*passthroughCodec) Marshal(v interface{}) ([]byte, error) {
	return v.([]byte), nil
}

// Name implements encoding.Codec.
func (*passthroughCodec) Name() string {
	return "passthrough"
}

// Unmarshal implements encoding.Codec.
func (*passthroughCodec) Unmarshal(data []byte, v interface{}) error {
	*v.(*[]byte) = data
	return nil
}

func fatal(a ...any) {
	fmt.Fprintln(os.Stderr, a...)
	os.Exit(1)
}

func main() {
	var (
		target string
		method string
		in     string
		n      int
		m      int
	)
	flag.StringVar(&target, "t", "localhost:50051", "target")
	flag.StringVar(&method, "meth", "/hellogrpc.Greeter/SayHello", "full method")
	flag.StringVar(&in, "i", "", "hex data")
	flag.IntVar(&n, "n", 1, "concurrency")
	flag.IntVar(&m, "m", 1, "count")
	flag.Parse()
	cc, err := grpc.Dial(target, grpc.WithTransportCredentials(insecure.NewCredentials()))
	if err != nil {
		fatal("grpc.Dial", err)
	}

	inData, err := hex.DecodeString(in)
	if err != nil {
		fatal("hex.DecodeString", err)
	}
	var out []byte
	codecOpt := grpc.ForceCodec(&passthroughCodec{})

	var wg sync.WaitGroup
	wg.Add(n)

	for i := 0; i < n; i++ {
		go func(i int) {
			start := time.Now()
			for j := 0; j < m; j++ {
				err := cc.Invoke(context.Background(), method, inData, &out, codecOpt)
				if err != nil {
					fatal("invoke:", err)
				}
			}
			delta := time.Since(start)
			fmt.Printf("worker #%d @%d: %s\n", i, m, delta)
			wg.Done()
		}(i)
	}

	wg.Wait()

	if n == 1 && m == 1 {
		fmt.Println("response:", hex.EncodeToString(out))
	}

	cc.Close()
}
