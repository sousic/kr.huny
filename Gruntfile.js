/**
 * Created by dhjang on 2016-10-26.
 */
'use strict';
var dest_js_path = 'src/main/webapp/resources/js';
var dest_css_path = 'src/main/webapp/resources/css';

module.exports = function (grunt) {
    grunt.initConfig({
        pkg : grunt.file.readJSON('package.json'),

        copy : {
            jquery : {
                src : 'node_modules/jquery-1x/node_modules/jquery/dist/jquery.js',
                dest : dest_js_path + '/jquery/jquery.min.js'
            },
            bootstrap : {
                src : 'node_modules/bootstrap/dist/js/bootstrap.min.js',
                dest : dest_js_path + '/bootstrap/bootstrap.min.js'
            },
            bootstrap_css : {
                src : 'node_modules/bootstrap/dist/css/bootstrap.min.css',
                dest : dest_css_path + '/bootstrap/bootstrap.min.css'
            },
            bootstrap_validater : {
                src : 'node_modules/bootstrap-validator/dist/validator.min.js',
                dest : dest_js_path + '/bootstrap/validator.min.js'
            },
            bootpage : {
                src : 'node_modules/bootpag/lib/jquery.bootpag.js',
                dest : dest_js_path + '/jquery/jquery.bootpag.js'
            },
            handlebars : {
                src : 'node_modules/handlebars/dist/handlebars.js',
                dest : dest_js_path + '/handlebars.js'
            },
            summernote : {
                src :  'node_modules/summernote/dist/summernote.min.js',
                dest :  dest_js_path + '/summernote/summernote.min.js'
            },
            summernote_js : {
                src : 'node_modules/summernote/dist/lang/summernote-ko-KR.min.js',
                dest : dest_js_path + '/summernote/lang/summernote-ko-KR.min.js'
            },
            summernote_css : {
                src : 'node_modules/summernote/dist/summernote.css',
                dest : dest_css_path + '/summernote/summernote.css'
            },
            summernote_font : {
                filter:'isFile',
                expand:true,
                flatten:true,
                src : 'node_modules/summernote/dist/font/*',
                dest : dest_css_path + '/summernote/font/'
            },
            font_awesome : {
                src : 'node_modules/font-awesome/css/font-awesome.min.css',
                dest : dest_css_path + '/font-awesome/font-awesome.min.css'
            },
            font_asesome_fonts : {
                filter: 'isFile',
                expand: true,
                flatten: true,
                src: 'node_modules/font-awesome/fonts/*',
                dest: dest_css_path + '/fonts/'
            }
            /*,
            angular : {
                src : 'node_modules/angular/angular.min.js',
                dest : dest_js_path + '/angular/angular.min.js'
            }*/
        },

        /*cssmin : {
            bootstrap: {
                src : 'node_modules/bootstrap/dist/css/bootstrap.min.css',
                dest : dest_css_path + '/bootstrap/bootstrap.min.css'
            }
        }*/
    });

    //플러그인 load
    grunt.loadNpmTasks('grunt-contrib-copy');
    //grunt.loadNpmTasks('grunt-css');

    //명령서 실행
    grunt.registerTask('default',['copy']);
};
