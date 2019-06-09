#!/usr/bin/perl 
use HTTP::Daemon;
use HTTP::Status;  
#use IO::File;

my $d = HTTP::Daemon->new(
            LocalAddr => 'localhost',
        )|| die;
print "Please contact me at: <URL:", $d->url, ">\n";

if ($ARGV[0] eq 'zad1') {
    while (my $c = $d->accept) {
        while (my $r = $c->get_request) {
            $response = HTTP::Response->new;
            $response->code(200);
            $response->content($r->headers_as_string);
            $c->send_response($response);
        }   
        $c->close;
        undef($c);
    } 
} elsif ($ARGV[0] eq 'zad2') {
    while (my $c = $d->accept) {
        while (my $r = $c->get_request) {
            if ($r->method eq 'GET') {
                if ($r->uri eq '/') {
                    $path = "./www/index.html";
                } else {
                    $path = "./www" . $r->uri;
                }
                $c->send_file_response($path);
            } else {
                $c->send_error(RC_FORBIDDEN)
            }
        }
        $c->close;
        undef($c);
    }
}
