module.exports = function(grunt) {
    'use strict';

    var rename = function (dest, src) { return dest + src.substring(0, src.indexOf('.min')) + '.js';};
    var renameVersion = function (dest, src) { return dest + src.substring(0, src.indexOf('-')) + '.js'; };

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        watch: {
            js: {
                files: [
                    'src/main/webapp/resources/**/*.js',
                    'src/main/webapp/resources/**/*.less',
                    'src/main/webapp/resources/**/*.html',
                    'src/main/webapp/WEB-INF/views/*.jsp',
                    'src/main/webapp/test/spec/**/*.js'
                ],
                tasks: ['clear', 'jshint:all'],
                options: {
                    livereload: true
                }
            }
        },
        copy: {
            dev: {
                files: [
                    {expand: true, cwd: 'bower_components/angular/', src: ['angular.js'], dest: 'src/main/webapp/resources/lib/'},
                    {expand: true, cwd: 'bower_components/angular-resource/', src: ['angular-resource.js'], dest: 'src/main/webapp/resources/lib/'},
                    {expand: true, cwd: 'bower_components/angular-route/', src: ['angular-route.js'], dest: 'src/main/webapp/resources/lib/'},
                    {expand: true, cwd: 'bower_components/angular-animate/', src: ['angular-animate.js'], dest: 'src/main/webapp/resources/lib/'},
                    {expand: true, cwd: 'bower_components/angular-cookies/', src: ['angular-cookies.js'], dest: 'src/main/webapp/resources/lib/'},
                    {expand: true, cwd: 'bower_components/angular-touch/', src: ['angular-touch.js'], dest: 'src/main/webapp/resources/lib/'},

                    {expand: true, cwd: 'bower_components/bootstrap/less', src: ['*.less'], dest: 'src/main/webapp/resources/css/bootstrap/'},
                    {expand: true, cwd: 'bower_components/less.js/dist/', src: ['less-1.4.2.js'], dest: 'src/main/webapp/resources/lib/', rename: renameVersion},

                    {expand: true, cwd: 'bower_components/lodash/', src: ['lodash.js'], dest: 'src/main/webapp/resources/lib/'},
                    {expand: true, cwd: 'bower_components/momentjs/', src: ['moment.js'], dest: 'src/main/webapp/resources/lib/'},
                    {expand: true, cwd: 'bower_components/angular-ui-bootstrap-bower/', src: ['ui-bootstrap.js'], dest: 'src/main/webapp/resources/lib/'}
                ]
            },
            release: {
                files: [
                    {expand: true, cwd: 'bower_components/angular/', src: ['angular.min.js'], dest: 'src/main/webapp/resources/lib/', rename: rename},
                    {expand: true, cwd: 'bower_components/angular-resource/', src: ['angular-resource.min.js'], dest: 'src/main/webapp/resources/lib/', rename: rename},
                    {expand: true, cwd: 'bower_components/angular-route/', src: ['angular-route.min.js'], dest: 'src/main/webapp/resources/lib/', rename: rename},
                    {expand: true, cwd: 'bower_components/angular-animate/', src: ['angular-animate.min.js'], dest: 'src/main/webapp/resources/lib/', rename: rename},

                    {expand: true, cwd: 'bower_components/lodash/dist/', src: ['lodash.min.js'], dest: 'src/main/webapp/resources/lib/', rename: rename},
                    {expand: true, cwd: 'bower_components/momentjs/min/', src: ['moment.min.js'], dest: 'src/main/webapp/resources/lib/', rename: rename},
                    {expand: true, cwd: 'bower_components/less.js/dist/', src: ['less-1.4.2.min.js'], dest: 'src/main/webapp/resources/lib/', rename: renameVersion},
                    {expand: true, cwd: 'bower_components/angular-ui-bootstrap-bower/', src: ['ui-bootstrap.min.js'], dest: 'src/main/webapp/resources/lib/', rename: rename}

                ]
            }
        },
        shell: {
            bower: {
                command: 'bower install',
                options: {
                    stdout: true
                }
            }
        },
        jshint: {
            options: {
                jshintrc: '.jshintrc'
            },
            all: [
                'Gruntfile.js',
                'src/main/webapp/resources/scripts/{,*/}*.js',
                'src/main/webapp/test/spec/{,*/}*.js'
            ]
        },
        less: {
            dist: {
                files: {
                    "src/main/webapp/resources/css/build.css": "src/main/webapp/resources/css/master.less"
                }
            }
        },
        usemin: {
            html: 'src/main/webapp/WEB-INF/views/index.jsp'
        },
        concat: {
            dist: {
                src: ['src/main/webapp/resources/scripts/**/*.js', 'src/main/webapp/resources/scripts/*.js'],
                dest: 'src/main/webapp/resources/scripts/build.js'
            }
        },
        clean: ['src/main/webapp/resources/scripts/build.js', 'src/main/webapp/resources/css/build.css']
    });

    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-less');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-clear');
    grunt.loadNpmTasks('grunt-shell');
    grunt.loadNpmTasks('grunt-usemin');

    grunt.registerTask('default', ['package']);
    grunt.registerTask('package', ['shell:bower', 'copy:dev']);

    grunt.registerTask('build', ['clean', 'jshint', 'concat', 'less', 'usemin']);
};